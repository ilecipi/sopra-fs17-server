package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AddStoneToShipMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.Move;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
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
//    @Autowired
//    private TempleRepository templeRepo;
    @Autowired
    private SiteBoardRepository siteBoardRepo;


    public Move getMove(Long moveId){
        return moveRepo.findOne(moveId);
    }

    public void addStoneToShip(Long gameId,Long roundId, Long shipId, String playerToken, int position){
        Round round = roundRepo.findById(roundId);
        Game game = gameRepo.findOne(gameId);
        User user = userRepo.findByToken(playerToken);
        AShip ship = shipRepo.findById(shipId);
        if(user == game.getCurrentPlayer() && ship.getStones()[position] == null && round != null
                && position<ship.getStones().length && game.getRounds().lastIndexOf(round) == game.getRounds().size()-1) {
            Move move = new AddStoneToShipMove(gameRepo.findOne(gameId), userRepo.findByToken(playerToken), shipRepo.findById(shipId), position,round);
            move = moveRepo.save(move);
            game = move.makeMove(game);
            round.getMoves().add(move);
            roundRepo.save(round);
            gameRepo.save(game);
        }
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
            Move move = new SailShipMove(game,user,ship,round,siteBoard);

            move = moveRepo.save(move);
            game = move.makeMove(game);
            round.getMoves().add(move);
            roundRepo.save(round);
            gameRepo.save(game);
        }
    }
}
