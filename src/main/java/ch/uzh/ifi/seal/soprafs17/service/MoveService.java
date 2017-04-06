package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AddStoneToShipMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
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

/**
 * Created by erion on 29.03.17.
 */
@Service
@Transactional
public class MoveService {


    @Autowired
    private ShipRepository shipRepo;

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoundRepository roundRepo;

    @Autowired
    private MoveRepository moveRepo;

    @Autowired
    private SiteBoardRepository siteBoardRepo;

    @Autowired
    private RuleBook ruleBook;

    @Autowired
    private ValidatorManager validatorManager;


    public AMove getMove(Long moveId){
        return moveRepo.findOne(moveId);
    }

    public void addStoneToShip(Game game, AMove move){
        ruleBook.applyRule(game,move);
    }
    public void addStoneToTemple(Game game,SailShipMove move){
        StoneBoard temple = (Temple)move.getSiteBoard();
        User player = move.getUser();
        AShip dockedShip = move.getShip();
        if(player == game.getCurrentPlayer() && temple.getDiscriminatorValue().equals("temple")){
            for(Stone s :dockedShip.getStones()){
                temple.addStone(s);
            }
            userRepo.save(player);
            game.findNextPlayer();
            int index = (game.getPlayers().lastIndexOf(game.getCurrentPlayer())+1)%game.getPlayers().size();
            game.setNextPlayer(game.getPlayers().get(index));
            gameRepo.save(game);
            gameRepo.save(game);
            siteBoardRepo.save(temple);
        }
    }
    public void sailShip(Game game,AMove move){
        ruleBook.applyRule(game,move);
        }
    public void getStone(Game game,AMove move){
        ruleBook.applyRule(game,move);
    }
}
