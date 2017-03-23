package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.IShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.ShipFactory;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.ShipRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.TempleRepository;
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

//        //4 player Ships' cards
//        List<List<ShipFactory>> fourPlayerShips = new ArrayList<List<ShipFactory>>(){{
//
//
//            add(new ArrayList<ShipFactory>(){{
//                 add(new ShipFactory().createFourSeatedShip());
//                 add(new ShipFactory().createFourSeatedShip());
//                 add(new ShipFactory().createTwoSeatedShip());
//                 add(new ShipFactory().createOneSeatedShip());
//
//             }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//
//            }});
//    }};
//
//        //3 players Ships' cards
//        List<ArrayList<ShipFactory>> threePlayerShips = new ArrayList<ArrayList<ShipFactory>>(){{
//
//
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createOneSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createOneSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createOneSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createOneSeatedShip());
//
//            }});
//        }};
//
//        //2 players Ship's cards
//        List<ArrayList<ShipFactory>> twoPlayerShips = new ArrayList<ArrayList<ShipFactory>>(){{
//
//
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createOneSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createOneSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createOneSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createOneSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createOneSeatedShip());
//
//            }});
//        }};

        public String addShips(Long gameId){
//            Game game = gameRepo.findOne(gameId);
//            Temple temple = new Temple(game.getPlayers().size());
//            System.out.println(game.getPlayers().size());
//            game.getSiteBoards().add(temple);
//            temple.setGame(game);
//            temple=templeRepo.save(temple);
//
//            gameRepo.save(game);
                Game game = gameRepo.findOne(gameId);
                int countPlayer = game.getPlayers().size();
                Random rn = new Random();
                int cardToDelete = rn.nextInt()%7;
                int selectCard = rn.nextInt()%6;
//                if(countPlayer==4){
////                    fourPlayerShips.remove(cardToDelete);
//                    game.setShips(fourPlayerShips.get(selectCard));
//
//                    game=gameRepo.save(game);
//                }
//                else if (countPlayer==3){
////                    threePlayerShips.remove(cardToDelete);
//                    game.setShips(threePlayerShips.get(selectCard));
//
//                    game=gameRepo.save(game);
//                }else{
////                    twoPlayerShips.remove(cardToDelete);
//                    game.setShips(twoPlayerShips.get(selectCard));
//
//                    game=gameRepo.save(game);
//                }
                  ShipFactory shipFactory = new ShipFactory();
////                  shipFactory.getShips().add(shipFactory.createFourSeatedShip());
//            shipFactory.setShips(new ArrayList<ShipFactory>());
//                  shipFactory=shipRepo.save(shipFactory);
//                  shipFactory.getShips().add(shipFactory.createFourSeatedShip());
//
//            shipFactory.setGame(game);
//            shipFactory=shipRepo.save(shipFactory);
//            game.setShips(shipFactory.getShips());
//                shipFactory.setGame(game);
                shipFactory = shipRepo.save(shipFactory);
//                game.setShips(shipFactory.getShips());
                game=gameRepo.save(game);

            return "/game/"+gameId + "/" + "ships";
        }

        public ShipFactory getShips(Long gameId){
             ShipFactory ships = gameRepo.findOne(gameId).getShips().get(0);
            return ships;
        }
}
