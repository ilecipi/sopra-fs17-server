package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.*;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.*;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Market;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import ch.uzh.ifi.seal.soprafs17.service.ruleEngine.RuleBook;
import ch.uzh.ifi.seal.soprafs17.service.validatorEngine.ValidatorManager;
import ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception.ValidationException;
import ch.uzh.ifi.seal.soprafs17.web.rest.GenericResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erion on 29.03.17.
 */
@Service("moveService")
@Transactional
public class MoveService {


    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private SiteBoardRepository siteBoardRepository;

    @Autowired
    private MarketCardRepository marketCardRepository;

    @Autowired
    private RuleBook ruleBook;

    @Autowired
    private ValidatorManager validatorManager;

    @Autowired
    private RoundService roundService;


    @Autowired
    public MoveService(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }


    public AMove getMove(Long moveId) {
        return moveRepository.findOne(moveId);
    }

    public void addStoneToShip(Game game, AMove move) {
        ruleBook.apply(game, move);
    }


    public void addStoneToShip(Long gameId, String playerToken, Long shipId, Long roundId, int position) {
        Game game = gameRepository.findOne(gameId);
        User user = userRepository.findByToken(playerToken);
        AShip ship = shipRepository.findById(shipId);
        Round round = roundRepository.findById(roundId);
        AddStoneToShipMove move = new AddStoneToShipMove(game, user, ship, position, round);
        validatorManager.validate(game, move);
        moveRepository.save(move);
        ruleBook.apply(game, move);
        game.updateCounterChanges();
        gameRepository.save(game);
        userRepository.save(user);
        shipRepository.save(ship);
        roundRepository.save(round);
    }

    public void addStoneToSiteBoard(Long siteBoardId, String playerToken, Long gameId, Long shipId) {
        //TODO: no REPOSITORY IN HERE
        StoneBoard stoneBoard = siteBoardRepository.findById(siteBoardId);
        Game game = gameRepository.findOne(gameId);
        User player = userRepository.findByToken(playerToken);
        AShip dockedShip = shipRepository.findById(shipId);
        if (player == game.getCurrentPlayer()) {
            //if the Lever card is played
            if (game.getCurrentRound().isActionCardLever()) {
                List<String> tmp = new ArrayList<>();
                for (int i = dockedShip.getStones().length - 1; i >= 0; i--) {
                    if (dockedShip.getStones()[i] != null) {
                        tmp.add(dockedShip.getStones()[i].getColor());
                    }
                }
                dockedShip.setDocked(true);
                dockedShip.setSiteBoard(stoneBoard);
                game.setTmpSiteBoardId(siteBoardId);
                game.getCurrentRound().setListActionCardLever(tmp);
                roundRepository.save(game.getCurrentRound());
            } else {
                for (int i = dockedShip.getStones().length - 1; i >= 0; i--) {
                    if (dockedShip.getStones()[i] != null) {
                        stoneBoard.addStone(dockedShip.getStones()[i]);
                    }
                }
            }
            siteBoardRepository.save(stoneBoard);
            userRepository.save(player);
            if (!game.getCurrentRound().isActionCardLever()) {
                game.findNextPlayer();
            }
            game.updateCounterChanges();
            gameRepository.save(game);
        }
    }

    public void addStoneToSiteBoard(Game game) {
        //if the Lever card is played
        StoneBoard stoneBoard = siteBoardRepository.findById(game.getTmpSiteBoardId());
        if (game.getCurrentRound().isActionCardLever()) {
            if (stoneBoard != null && !stoneBoard.getDiscriminatorValue().equals("market")) {
                for (Stone s : game.getCurrentRound().getStonesLeverCard()) {
                    ((StoneBoard) stoneBoard).addStone(s);
                    siteBoardRepository.save(stoneBoard);
                }
                game.getCurrentRound().setActionCardLever(false);
                game.getCurrentRound().setListActionCardLever(new ArrayList<>());
                stoneBoard.setOccupied(true);
            }
        }
        siteBoardRepository.save(stoneBoard);
        gameRepository.save(game);
    }

    public void sailShip(Long gameId, Long roundId, Long shipId, String playerToken, String siteBoardsType) {
        Game game = gameRepository.findOne(gameId);
        User user = userRepository.findByToken(playerToken);
        AShip ship = shipRepository.findById(shipId);
        SiteBoard siteBoard = this.findSiteboardsByType(siteBoardsType.toLowerCase(), gameId);
        Round round = roundRepository.findById(roundId);
        if (game != null && user != null && ship != null && siteBoard != null && round != null) {
            SailShipMove move = new SailShipMove(game, user, ship, round, siteBoard);
            validatorManager.validate(game, move);
            ruleBook.apply(game, move);

            if (move.getSiteBoard() instanceof StoneBoard) {
                this.addStoneToSiteBoard(siteBoard.getId(), playerToken, gameId, shipId);
                if (!round.isActionCardLever()) {
                    game.collectPoints();
                }

                //Neither an ImmediateCard nor an ActionCardLever is being played
                if (!round.isImmediateCard() && !round.isActionCardLever()) {

                    roundService.addRound(game.getId());
                }
            }
            //It's the Market
            else {
                //If the ActionCardLever is not played
                if (!game.getCurrentRound().isActionCardLever()) {
                    this.addUserToMarket(game, ship);
                }
                //If the ActionCardLever is played
                else {
                    //array of stones for the client
                    List<String> tmp = new ArrayList<>();
                    for (int i = ship.getStones().length - 1; i >= 0; i--) {
                        if (ship.getStones()[i] != null) {
                            tmp.add(ship.getStones()[i].getColor());
                        }
                    }

                    ship.setDocked(true);
                    ship.setSiteBoard(game.getMarket());
                    //Save the Market Id for later
                    game.setTmpSiteBoardId(game.getMarket().getId());
                    game.getCurrentRound().setListActionCardLever(tmp);
                    roundRepository.save(game.getCurrentRound());
                }
            }
            game.updateCounterChanges();
            siteBoardRepository.save(siteBoard);
            gameRepository.save(game);
            userRepository.save(user);
            shipRepository.save(ship);
            roundRepository.save(round);
        }


    }

    public void getStone(Long gameId, Long roundId, String playerToken) {
        Game game = gameRepository.findOne(gameId);
        User user = userRepository.findByToken(playerToken);
        Round round = roundRepository.findById(roundId);
        if (game != null && user != null && round != null) {
            GetStoneMove move = new GetStoneMove(user, round, game);
            validatorManager.validate(game, move);
            moveRepository.save(move);
            ruleBook.apply(game, move);
            game.updateCounterChanges();
            gameRepository.save(game);
            roundRepository.save(round);
            userRepository.save(user);
        }
    }

    public SiteBoard findSiteboardsByType(String siteBoardType, Long gameId) {
        List<SiteBoard> siteBoards = gameRepository.findOne(gameId).getSiteBoards();
        SiteBoard siteBoard = null;
        if (!siteBoards.isEmpty()) {
            for (SiteBoard s : siteBoards) {
                if (s.getDiscriminatorValue().equals(siteBoardType)) {
                    siteBoard = s;
                }
            }
        }
        return siteBoard;
    }

    public void addUserToMarket(Game game, AShip ship) {
        if (!game.getCurrentRound().isActionCardLever()) {
            List<SiteBoard> siteBoards = game.getSiteBoards();
            Market market = null;
            if (!siteBoards.isEmpty()) {
                for (SiteBoard s : siteBoards) {
                    if (s.getDiscriminatorValue().equals("market")) {
                        market = (Market) s;
                    }
                }
            }
            for (int i = ship.getStones().length - 1; i >= 0; i--) {
                if (ship.getStones()[i] != null) {
                    market.addUser(ship.getStones()[i].getColor());
                    for(User u: game.getPlayers()){
                        if(ship.getStones()[i].getColor().equals(u.getColor())){
                            u.setStoneQuarry(u.getStoneQuarry()+1);
                        }
                    }
                }
            }
            game.findNextPlayer();
        } else {
            List<String> tmp = new ArrayList<>();
            for (int i = ship.getStones().length - 1; i >= 0; i--) {
                if (ship.getStones()[i] != null) {
                    tmp.add(ship.getStones()[i].getColor());
                }
            }
            game.getCurrentRound().setListActionCardLever(tmp);
            roundRepository.save(game.getCurrentRound());
            List<String> userColors = new ArrayList<>();
            for (Stone s : game.getCurrentRound().getStonesLeverCard()) {
                userColors.add(s.getColor());
            }
            Market market = game.getMarket();
            market.setUserColor(userColors);
            game.updateCounterChanges();
            siteBoardRepository.save(market);
        }
    }

    public void addUserToMarketLever(Game game) {
        roundRepository.save(game.getCurrentRound());
        List<String> userColors = new ArrayList<>();
        for (Stone s : game.getCurrentRound().getStonesLeverCard()) {
            userColors.add(s.getColor());
        }
        Market market = game.getMarket();
        market.setUserColor(userColors);
        game.getCurrentRound().setActionCardLever(false);
        game.getCurrentRound().setListActionCardLever(new ArrayList<>());
        market.setOccupied(true);
        game.updateCounterChanges();
        siteBoardRepository.save(market);
    }

    public void giveCardToUser(Long gameId, Long roundId, String playerToken, int position) {
        Game game = gameRepository.findOne(gameId);
        User user = userRepository.findByToken(playerToken);
        Round round = roundRepository.findById(roundId);
        if (game != null && user != null && round != null) {
            GiveCardToUserMove move = moveRepository.save(new GiveCardToUserMove(user, round, game, position));
            validatorManager.validate(game, move);
            moveRepository.save(move);

            ruleBook.apply(game, move);
            Market market = game.getMarket();
            if (market.getUserColor().isEmpty() && market.isOccupied()) {
                if (!round.isImmediateCard()) {
                    int counterCard = user.getMarketCards().size() - 1;
                    marketCardRepository.save(user.getMarketCards().get(counterCard));
                    game.collectPoints();
                    roundService.addRound(game.getId());
                }
            }
            game.updateCounterChanges();
            user = userRepository.save(user);
            gameRepository.save(game);
            roundRepository.save(round);

        }
    }

    public void playMarketCard(Long gameId, Long roundId, String playerToken, Long marketCardId) {
        Game game = gameRepository.findOne(gameId);
        User user = userRepository.findByToken(playerToken);
        Round round = roundRepository.findById(roundId);
        AMarketCard marketCard = marketCardRepository.findById(marketCardId);

        if (game != null && user != null && round != null) {
            PlayMarketCardMove move = new PlayMarketCardMove(user, round, game, marketCard);
            validatorManager.validate(game, move);
            moveRepository.save(move);
            ruleBook.apply(game, move);
            game.setDiscardedCardsCounter(game.getDiscardedCardsCounter()+1);
            Market market = game.getMarket();
            if (market.getUserColor().isEmpty() && market.isOccupied() && !round.isImmediateCard()) {
                game.collectPoints();
                roundService.addRound(game.getId());
            }
            game.collectPoints();
            marketCard.setPlayed(true);
            marketCardRepository.save(marketCard);
            userRepository.save(user);
            game.updateCounterChanges();
            gameRepository.save(game);
            roundRepository.save(round);
        }
    }

    public void playLeverCard(Long gameId,Long roundId,String playerToken, List<String> userColors) {
        Game game = gameRepository.findOne(gameId);
        User user = userRepository.findByToken(playerToken);
        Round round = roundRepository.findById(roundId);

        if (game != null && user != null && round != null) {
            Stone[] tmpStones = new Stone[userColors.size()];
            for (int i = 0; i < tmpStones.length; i++) {
                tmpStones[i] = new Stone(userColors.get(i));
            }
            PlayLeverCardMove move = new PlayLeverCardMove(user, round, game, tmpStones);
            moveRepository.save(move);
            //VALIDATOR
            ruleBook.apply(game, move);
            this.addLeverUser(game);
            round.setActionCardLever(false);
        }
        game.updateCounterChanges();
        userRepository.save(user);
        gameRepository.save(game);
        roundRepository.save(round);

    }

    public void addLeverUser(Game game) {
        Long id = game.getTmpSiteBoardId();
        if (game.getMarket().getId().equals(id)) {
            this.addUserToMarketLever(game);
        } else {
            this.addStoneToSiteBoard(game);
        }
        game.getCounterChanges();
        if (game.getMarket().getUserColor().size() == 0) {
            game.findNextPlayer();
            game.collectPoints();
            roundService.addRound(game.getId());
        }
    }
}
