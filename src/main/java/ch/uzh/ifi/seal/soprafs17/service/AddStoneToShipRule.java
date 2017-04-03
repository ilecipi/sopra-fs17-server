package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AddStoneToShipMove;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by erion on 03.04.17.
 */
@Service
@Transactional
public class AddStoneToShipRule implements IRule {

    @Autowired
    private MoveService moveService;
    @Autowired
    private GameRepository gameRepo;
    @Autowired
    private RoundRepository roundRepo;
    @Autowired
    private ShipRepository shipRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private MoveRepository moveRepo;
    public boolean supports(AMove move){
        return move instanceof AddStoneToShipMove;
    }

    @Override
    public void apply(Game game, AMove move) {
//        System.out.println("GAYYY");
//        gameRepo.save(game);
//        if(gameRepo==null){
//            System.out.println("game is GAY1");
//        }
//        game = gameRepo.findOne(game.getId());
////        game =gameRepo.save(game);
//        if(supports(move)){
//
//            System.out.println("GAYY222222");
//            if(move.getRound()==null){
//                System.out.println("ROUND NULL");
//            }
//            if(move.getUser()==null){
//                System.out.println("USER NULL");
//            }
//
//            Round round = move.getRound();
//            User user = move.getUser();
            AddStoneToShipMove castedMove = (AddStoneToShipMove)move;
//            AShip ship = castedMove.getShip();
//            if(castedMove.getShip()==null){
//                System.out.println("SHIP NULL");
//            }
//            if(castedMove.getRound()==null){
//                System.out.println("CASTROUND NULL");
//            }
//            if(castedMove.getUser()==null){
//                System.out.println("CASTUSER NULL");
//            }
////            AMove  = new AddStoneToShipMove(game, user, ship, castedMove.getPosition(),round);
//            System.out.println("GAY333");
////            moveRepo.save(move);
//            System.out.println("castedMove SAVED");
//            game=castedMove.makeMove(game);
//            if(round.getAMoves()!=null){
//                System.out.println("ROUND MOVE not NULL");
//            }else{
//                System.out.println("ROUND NULL");
//            }
//            System.out.println(castedMove);
//            round.getAMoves().add(move);
////            roundRepo.save(round);
//            if(gameRepo==null){
//                System.out.println("game is GAY");
//            }else {
//                System.out.println(game.getId());
//                game =gameRepo.save(game);
//            }
//
//        }
        System.out.println("GAYYY4444");
    }


}
