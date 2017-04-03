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
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.RuleBook;
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


    public AMove getMove(Long moveId){
        return moveRepo.findOne(moveId);
    }

    public void addStoneToShip(Long gameId,Long roundId, Long shipId, String playerToken, int position){
        Game game = gameRepo.findOne(gameId);
        User user = userRepo.findByToken(playerToken);
        AShip ship = shipRepo.findById(shipId);
        Round round = roundRepo.findById(roundId);
        ruleBook.apply(gameRepo.findOne(gameId),moveRepo.save(new AddStoneToShipMove(game,user,ship,position,round)));
        gameRepo.save(game);
        userRepo.save(user);
        shipRepo.save(ship);
        roundRepo.save(round);
    }
    public void addStoneToTemple(Long templeId,String playerToken,Long gameId,Long shipId){
        StoneBoard temple = siteBoardRepo.findById(templeId);
        Game game = gameRepo.findOne(gameId);
        User player = userRepo.findByToken(playerToken);
        AShip dockedShip = shipRepo.findById(shipId);
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

    public void sailShip(Long gameId,Long roundId, Long shipId, String playerToken,Long siteBoardId){
        Round round = roundRepo.findById(roundId);
        Game game = gameRepo.findOne(gameId);
        User user = userRepo.findByToken(playerToken);
        AShip ship = shipRepo.findById(shipId);
        SiteBoard siteBoard = siteBoardRepo.findById(siteBoardId);
        if(user == game.getCurrentPlayer() && round.getShips().contains(ship) && ship.isReady() && !ship.isDocked() && !siteBoard.isOccupied()){
            AMove AMove = new SailShipMove(game,user,ship,round,siteBoard);

            AMove = moveRepo.save(AMove);
            game = AMove.makeMove(game);
            round.getAMoves().add(AMove);
            roundRepo.save(round);
            gameRepo.save(game);
        }
    }
}
