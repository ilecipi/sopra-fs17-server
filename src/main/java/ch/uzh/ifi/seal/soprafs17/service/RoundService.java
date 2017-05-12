package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.*;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.*;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Market;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception.NullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by ilecipi on 27.03.17.
 */
@Service("roundService")
@Transactional
public class RoundService {

    private final Logger log = LoggerFactory.getLogger(ch.uzh.ifi.seal.soprafs17.service.RoundService.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private SiteBoardRepository siteBoardsRepository;


    @Autowired
    private SiteBoardsService siteBoardsService;

    @Autowired
    private ShipService shipService;

    @Autowired
    private GameService gameService;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private MarketCardRepository marketCardRepository;

    @Autowired
    public RoundService(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    public static final int MAX_ROUNDS_POSSIBLE = 6;

    private boolean allShipsSailed;

    public List<Round> listRounds() {
        List<Round> result = new ArrayList<>();
        roundRepository.findAll().forEach(result::add);
        return result;
    }

    public Round getSpecificRound(Long roundId) {
        return roundRepository.findById(roundId);
    }

    public void addRound(Long gameId) {
        Game game = gameRepository.findOne(gameId);
        boolean allShipsAreDocked = true;
        //if the game is started a round should exist
        if (game.getRounds().size() != 0) {
            for (AShip ship : game.getRounds().get(game.getRounds().size() - 1).getShips()) {
                if (!ship.isDocked()) {
                    allShipsAreDocked = false;
                }
            }
        }
        //end of game
        if (game.getRounds().size() >= MAX_ROUNDS_POSSIBLE && allShipsAreDocked) {
            game.setStatus(GameStatus.FINISHED);
            for (User u : game.getPlayers()) {
                u.setStatus(UserStatus.ONLINE);
                u.setSupplySled(0);
                for(AMarketCard mc : u.getMarketCards()){
                    mc.setUser(null);
                    game.updateCounterChanges();
                }
                u.setMarketCards(new ArrayList<>());
            }
        }
        //if a round can be added
        if (game.getRounds().size() < MAX_ROUNDS_POSSIBLE && allShipsAreDocked) {
            List<SiteBoard> siteBoards = game.getSiteBoards();
            if (!siteBoards.isEmpty()) {
                for (SiteBoard s : siteBoards) {
                    s.setOccupied(false);
                    s.setDockedShip(null);
                    if (!s.getDiscriminatorValue().equals("market")) {
                        if (((StoneBoard) s).isCounted()) {
                            s = (StoneBoard) s;
                            ((StoneBoard) s).setCounted(false);
                        }
                    }
                }
            }
            Round round = new Round();
            round.setShips(new ArrayList<AShip>());
            roundRepository.save(round);
            boolean notChosen = true;
            Map<Integer, Integer[]> shipsCards = game.getShipsCards();
            Random rn = new Random();
            int selectShip;
            int counter = 0;

            //avoid an infinite while loop using a counter
            while (notChosen && counter < 200) {
                counter++;
                selectShip = rn.nextInt() % 7;
                if (shipsCards.containsKey(selectShip)) {
                    for (Integer i : shipsCards.get(selectShip)) {
                        if (i == 1) {
                            round.getShips().add(shipRepository.save(new OneSeatedShip()));
                        } else if (i == 2) {
                            round.getShips().add(shipRepository.save(new TwoSeatedShip()));
                        } else if (i == 3) {
                            round.getShips().add(shipRepository.save(new ThreeSeatedShip()));
                        } else {
                            round.getShips().add(shipRepository.save(new FourSeatedShip()));
                        }

                    }
                    notChosen = false;
                    shipsCards.remove(selectShip);
                    round = roundRepository.save(round);
                    game = gameRepository.save(game);

                }
            }
            //Set the current market cards for the current round
            round.setMarketCards(new ArrayList<AMarketCard>());
            Map<Integer, String> marketCards = game.getMarketCards();
            for (int i = 0; i < 4; i++) {
                int toTake = marketCards.size() - 1;
                String card = marketCards.remove(toTake);
                if (card.equals("PAVED_PATH")) {
                    round.getMarketCards().add(marketCardRepository.save(new PavedPath()));
                } else if (card.equals("SARCOPHAGUS")) {
                    round.getMarketCards().add(marketCardRepository.save(new Sarcophagus()));
                } else if (card.equals("ENTRANCE")) {
                    round.getMarketCards().add(marketCardRepository.save(new Entrance()));
                } else if (card.equals("PYRAMID_DECORATION")) {
                    round.getMarketCards().add(marketCardRepository.save(new PyramidDecoration()));
                } else if (card.equals("TEMPLE_DECORATION")) {
                    round.getMarketCards().add(marketCardRepository.save(new TempleDecoration()));
                } else if (card.equals("BURIAL_CHAMBER_DECORATION")) {
                    round.getMarketCards().add(marketCardRepository.save(new BurialChamberDecoration()));
                } else if (card.equals("OBELISK_DECORATION")) {
                    round.getMarketCards().add(marketCardRepository.save(new ObeliskDecoration()));
                } else if (card.equals("STATUE")) {
                    round.getMarketCards().add(marketCardRepository.save(new Statue()));
                } else if (card.equals("SAIL")) {
                    round.getMarketCards().add(marketCardRepository.save(new Sail()));
                } else if (card.equals("CHISEL")) {
                    round.getMarketCards().add(marketCardRepository.save(new Chisel()));
                } else if (card.equals("HAMMER")) {
                    round.getMarketCards().add(marketCardRepository.save(new Hammer()));
                } else if (card.equals("SAIL")) {
                    round.getMarketCards().add(marketCardRepository.save(new Sail()));
                } else if (card.equals("LEVER")) {
                    round.getMarketCards().add(marketCardRepository.save(new Lever()));
                } else {
                    throw new NullException();
                }
                round = roundRepository.save(round);
                game = gameRepository.save(game);
            }
            game.getRounds().add(round);
            gameRepository.save(game);
            round.setGame(game);
            roundRepository.save(round);
            Market market = game.getMarket();
            int currentRound = game.getRounds().size() - 1;
            Round round1 = game.getRounds().get(currentRound);
            market.setMarketCards(round1.getMarketCards());
            game.updateCounterChanges();
            for(String c : game.getColors().keySet()){
                if(!game.getColors().get(c)&&game.getPoints().containsKey(c)){
                    game.getPoints().remove(c);
                }
            }
            gameRepository.save(game);
            roundRepository.save(round1);
            siteBoardsRepository.save(market);
        }

    }


}
