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

        ShipFactory shipFactory = new ShipFactory();
        //4 player ships' cards
        List<ShipFactory[]> fourPlayerShips = new ArrayList<ShipFactory[]>(){{
            add(new ShipFactory[]{shipFactory.createFourSeatedShip(),
                    shipFactory.createFourSeatedShip(),
                    shipFactory.createTwoSeatedShip(),
                    shipFactory.createOneSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createFourSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createTwoSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createFourSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createThreeSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createThreeSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createTwoSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createFourSeatedShip(),
                shipFactory.createFourSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createTwoSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createFourSeatedShip(),
                shipFactory.createFourSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createTwoSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createFourSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createTwoSeatedShip()});
    }};

        //3 players ships' cards
        List<ShipFactory[]> threePlayerShips = new ArrayList<ShipFactory[]>(){{


            add(new ShipFactory[]{shipFactory.createThreeSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createTwoSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createThreeSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createTwoSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createFourSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createOneSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createFourSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createTwoSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createFourSeatedShip(),
                shipFactory.createFourSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createOneSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createFourSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createOneSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createFourSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createOneSeatedShip()});
        }};

        //2 players Ship's cards
        List<ShipFactory[]> twoPlayerShips = new ArrayList<ShipFactory[]>(){{


            add(new ShipFactory[]{
                shipFactory.createFourSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createTwoSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createFourSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createOneSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createFourSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createOneSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createThreeSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createOneSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createThreeSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createTwoSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createThreeSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createOneSeatedShip()});
            add(new ShipFactory[]{
                shipFactory.createFourSeatedShip(),
                shipFactory.createThreeSeatedShip(),
                shipFactory.createTwoSeatedShip(),
                shipFactory.createOneSeatedShip()});
            }};

        private boolean firstInitialization = true;

        public String addShips(Long gameId) {
            Game game = gameRepo.findOne(gameId);
            int countPlayer = game.getPlayers().size();
            Random rn = new Random();
            if (countPlayer == 4) {
                if(firstInitialization) {
                    int cardToDelete = rn.nextInt() % 6;
                    fourPlayerShips.remove(cardToDelete);
                    firstInitialization=false;
                }
                int selectCard = rn.nextInt() %(2) ;
                System.out.println(fourPlayerShips.size());
                ShipFactory[] ships = fourPlayerShips.remove(selectCard);
                for (ShipFactory s : ships) {
                    s.setGame(game);
                    shipRepo.save(s);
                    game.getShips().add(s);
                }
            }
//                else if (countPlayer==3) {
//                if(firstInitialization) {
//                    int cardToDelete = rn.nextInt() % 6;
//                    threePlayerShips.remove(cardToDelete);
//                    firstInitialization=false;
//                }
//                int selectCard = rn.nextInt() % (threePlayerShips.size() - 1);
//                ShipFactory[] ships = threePlayerShips.remove(selectCard);
//                for (ShipFactory s : ships) {
//                    s.setGame(game);
//                    shipRepo.save(s);
//                    game.getShips().add(s);
//                }
//            }else {
//                if(firstInitialization) {
//                    int cardToDelete = rn.nextInt() % 6;
//                    twoPlayerShips.remove(cardToDelete);
//                    firstInitialization=false;
//                }
//                    int selectCard = rn.nextInt() %(twoPlayerShips.size()-1);
//                    ShipFactory[] ships = twoPlayerShips.get(selectCard);
//                    for (ShipFactory s : ships) {
//                        s.setGame(game);
//                        shipRepo.save(s);
//                        game.getShips().add(s);
//                }
//            }

            gameRepo.save(game);
            return "/game/" + gameId + "/" + "ships";
        }


        public ShipFactory getShips(Long gameId){
             ShipFactory ships = gameRepo.findOne(gameId).getShips().get(0);
            return ships;
        }

        public void addStone(Long gameId, Long shipId,Long playerToken,int position){
            ShipFactory ship = shipRepo.findById(shipId);
            Game game = gameRepo.findOne(gameId);
            User player = userRepo.findById(playerToken);
//            Stone[] stonesOfTheShip = ship.getStones();
            if(player == game.getCurrentPlayer() && ship.getStones()[position] == null){
                Stone stone = new Stone(player.getColor());
                ship.getStones()[position] = stone;
//                ship.setStones(stonesOfTheShip);

//                System.out.println(ship.getStones()[0]);
                shipRepo.save(ship);
                userRepo.save(player);
                game.findNextPlayer();
                gameRepo.save(game);
            }
        }
}
