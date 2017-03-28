package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.IShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.OneSeatedShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.ShipFactory;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.ShipRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.TempleRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ilecipi on 22.03.17.
 */

@Service
@Transactional
public class ShipService {

        @Autowired
        private ShipRepository shipRepo;

        @Autowired
        private GameRepository gameRepo;

        @Autowired
        private UserRepository userRepo;

//
//        private boolean firstInitialization = true;
//
//        public String addShips(Long gameId) {
//            Game game = gameRepo.findOne(gameId);
//            int countPlayer = game.getPlayers().size();
//            Random rn = new Random();
//            if (countPlayer == 4) {
//                if(firstInitialization) {
//                    int cardToDelete = rn.nextInt() % 6;
//                    fourPlayerShips.remove(cardToDelete);
//                    firstInitialization=false;
//                }
//                int selectCard = rn.nextInt() %(2) ;
//                System.out.println(fourPlayerShips.size());
//                ShipFactory[] ships = fourPlayerShips.remove(selectCard);
//                for (ShipFactory s : ships) {
//                    s.setGame(game);
//                    shipRepo.save(s);
//                    game.getShips().add(s);
//                }
//            }
////                else if (countPlayer==3) {
////                if(firstInitialization) {
////                    int cardToDelete = rn.nextInt() % 6;
////                    threePlayerShips.remove(cardToDelete);
////                    firstInitialization=false;
////                }
////                int selectCard = rn.nextInt() % (threePlayerShips.size() - 1);
////                ShipFactory[] ships = threePlayerShips.remove(selectCard);
////                for (ShipFactory s : ships) {
////                    s.setGame(game);
////                    shipRepo.save(s);
////                    game.getShips().add(s);
////                }
////            }else {
////                if(firstInitialization) {
////                    int cardToDelete = rn.nextInt() % 6;
////                    twoPlayerShips.remove(cardToDelete);
////                    firstInitialization=false;
////                }
////                    int selectCard = rn.nextInt() %(twoPlayerShips.size()-1);
////                    ShipFactory[] ships = twoPlayerShips.get(selectCard);
////                    for (ShipFactory s : ships) {
////                        s.setGame(game);
////                        shipRepo.save(s);
////                        game.getShips().add(s);
////                }
////            }
//
//            gameRepo.save(game);
//            return "/game/" + gameId + "/" + "ships";
//        }
//
//
//        public ShipFactory getShips(Long gameId){
//             ShipFactory ships = gameRepo.findOne(gameId).getShips().get(0);
//            return ships;
//        }
//
//        public void addStone(Long gameId, Long shipId,Long playerToken,int position){
//            ShipFactory ship = shipRepo.findById(shipId);
//            Game game = gameRepo.findOne(gameId);
//            User player = userRepo.findById(playerToken);
//            if(player == game.getCurrentPlayer() && ship.getStones()[position] == null){
//                Stone stone = new Stone(player.getColor());
//                ship.getStones()[position] = stone;
//                shipRepo.save(ship);
//                userRepo.save(player);
//                game.findNextPlayer();
//                int index = (game.getPlayers().lastIndexOf(game.getCurrentPlayer())+1)%game.getPlayers().size();
//                game.setNextPlayer(game.getPlayers().get(index));

//                gameRepo.save(game);
//            }
//        }
}

