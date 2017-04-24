package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.*;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Market;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import ch.uzh.ifi.seal.soprafs17.service.ruleEngine.RuleBook;
import ch.uzh.ifi.seal.soprafs17.service.validatorEngine.ValidatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        ruleBook.applyRule(game, move);
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
            gameRepository.save(game);
        }
    }

    public void addStoneToSiteBoard(Game game) {
        //if the Lever card is played
        StoneBoard stoneBoard = siteBoardRepository.findById(game.getTmpSiteBoardId());
        if (game.getCurrentRound().isActionCardLever()) {
            if (stoneBoard!=null&&!stoneBoard.getDiscriminatorValue().equals("market")) {
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

    public void sailShip(Game game, AMove move) {
        ruleBook.applyRule(game, move);
    }

    public void getStone(Game game, AMove move) {
        ruleBook.applyRule(game, move);
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
        if(!game.getCurrentRound().isActionCardLever()) {
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
                }
            }
            game.findNextPlayer();
        }else{
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
            siteBoardRepository.save(market);
        }

    public void giveCardToUser(Game game, AMove move) {
        ruleBook.applyRule(game, move);
    }

    public void playMarketCard(Game game, AMove move) {
        ruleBook.applyRule(game, move);
    }

    public void playLeverCard(Game game, AMove move) {
        ruleBook.applyRule(game, move);
    }

    public void addLeverUser(Game game){
        Long id  = game.getTmpSiteBoardId();
        if(game.getMarket().getId().equals(id)){
            this.addUserToMarketLever(game);
        }else{
            this.addStoneToSiteBoard(game);
        }
        if(game.getMarket().getUserColor().size()==0) {
            game.findNextPlayer();
            game.collectPoints();
            roundService.addRound(game.getId());
        }
    }
}
