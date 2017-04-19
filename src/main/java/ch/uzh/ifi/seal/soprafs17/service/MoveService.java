package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AddStoneToShipMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Market;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.RuleBook;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.ValidatorManager;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception.NotCurrentPlayerException;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public MoveService(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }


    public AMove getMove(Long moveId){
        return moveRepository.findOne(moveId);
    }

    public void addStoneToShip(Game game, AMove move){
        ruleBook.applyRule(game,move);
    }
    public void addStoneToSiteBoard(Long siteBoardId,String playerToken,Long gameId,Long shipId){
        //TODO: no REPOSITORY IN HERE
        StoneBoard stoneBoard = siteBoardRepository.findById(siteBoardId);
        Game game = gameRepository.findOne(gameId);
        User player = userRepository.findByToken(playerToken);
        AShip dockedShip = shipRepository.findById(shipId);
        if(player == game.getCurrentPlayer() /*&& temple.getDiscriminatorValue().equals("temple")*/){
            for(int i = dockedShip.getStones().length-1; i>=0;i--){
                if(dockedShip.getStones()[i] != null){
                    stoneBoard.addStone(dockedShip.getStones()[i]);
                }
            }
            siteBoardRepository.save(stoneBoard);
            userRepository.save(player);
            game.findNextPlayer();
            int index = (game.getPlayers().lastIndexOf(game.getCurrentPlayer())+1)%game.getPlayers().size();
            game.setNextPlayer(game.getPlayers().get(index));
            gameRepository.save(game);
        }
    }
    public void sailShip(Game game,AMove move){
        ruleBook.applyRule(game,move);
        }
    public void getStone(Game game,AMove move){
        ruleBook.applyRule(game,move);
    }
    public SiteBoard findSiteboardsByType(String siteBoardType,Long gameId){
        List<SiteBoard> siteBoards = gameRepository.findOne(gameId).getSiteBoards();
        SiteBoard siteBoard= null;
        if (!siteBoards.isEmpty()) {
            for (SiteBoard s : siteBoards) {
                if (s.getDiscriminatorValue().equals(siteBoardType)) {
                    siteBoard=s;
                }
            }
        }
        return siteBoard;
    }

    public void addUserToMarket(Game game, AShip ship){
        List<SiteBoard> siteBoards = game.getSiteBoards();
        Market market = null;
        if (!siteBoards.isEmpty()) {
            for (SiteBoard s : siteBoards) {
                if (s.getDiscriminatorValue().equals("market")) {
                    market= (Market) s;
                }
            }
        }
        for(int i = ship.getStones().length-1; i>=0;i--){
            if(ship.getStones()[i] != null){
                market.addUser(ship.getStones()[i].getColor());
            }
        }
    }

    public void giveCardToUser(Game game, AMove move){
            ruleBook.applyRule(game,move);
        }
}
