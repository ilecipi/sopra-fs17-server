//package ch.uzh.ifi.seal.soprafs17.service;
//
//import ch.uzh.ifi.seal.soprafs17.Application;
//import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
//import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
//import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
//import ch.uzh.ifi.seal.soprafs17.model.entity.User;
//import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
//import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AddStoneToShipMove;
//import ch.uzh.ifi.seal.soprafs17.model.entity.moves.GetStoneMove;
//import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
//import ch.uzh.ifi.seal.soprafs17.model.entity.ships.OneSeatedShip;
//import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
//import ch.uzh.ifi.seal.soprafs17.model.repository.MoveRepository;
//import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.IntegrationTest;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
///**
// * Created by erion on 11.04.17.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@IntegrationTest({"server.port=0"})
//public class MoveServiceTest {
//
//    @Autowired
//    MoveService moveService;
//
//    @Autowired
//    MoveRepository moveRepo;
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    GameService gameService;
//
//    @Autowired
//    RoundService roundService;
//
//    @Autowired
//    GameRepository gameRepo;
//
//    @Autowired
//    UserRepository userRepo;
//
//    @Before
//    public void init(){
//        User player = userService.createUser("testName","testUsername","t123",UserStatus.ONLINE,new ArrayList<>());
//        userRepo.save(player);
//        Game game = new Game();
//        game.setOwner(player.getName());
//        game.setName("testGame");
//        User playerTwo = userService.createUser("testName1","testUsername1","t1231",UserStatus.ONLINE,new ArrayList<>());
//        userRepo.save(playerTwo);
//        gameService.addGame(game,player.getToken());
//        gameService.startGame(game.getId(),player.getToken());
//    }
//    @Test
//    public void getMove() throws Exception {
////        List<Game> games = new ArrayList<>();
////        User user = userService.createUser("testName","testUsername","t123", UserStatus.IS_READY,games);
////        User user1 = userService.createUser("testName1","testUsername1","t1231", UserStatus.ONLINE,games);
////        userRepo.save(user);
////        userRepo.save(user1);
////        Game game = new Game();
////        game.setName("testGame");
////        game.setOwner(user.getName());
////        AMove move = new GetStoneMove(user,new Round(),game);
////        move.setId(new Long(123));
////        gameRepo.save(game);
////        gameService.addGame(game,user.getToken());
////        gameService.addUser(game.getId(),user1.getToken());
////        roundService.addRound(game.getId());
////        gameService.startGame(game.getId(),user.getToken());
////        assertSame(game.getRounds().get(0).getAMoves().get(0),move);
////        User player = userService.createUser("testName","testUsername","t123",UserStatus.ONLINE,new ArrayList<>());
//        User player = new User();
//        player.setId(new Long(123));
//        userRepo.save(player);
//        Game game = new Game();
//        game.setOwner(player.getName());
//        game.setName("testGame");
//        User playerTwo = userService.createUser("testName1","testUsername1","t1231",UserStatus.ONLINE,new ArrayList<>());
//        userRepo.save(playerTwo);
//        gameService.addGame(game,player.getToken());
//        gameService.startGame(game.getId(),player.getToken());
//
//
//
//
////        AMove move = new GetStoneMove(user,new Round(),game);
////        gameService.startGame(game.getId(),user.getToken());
// }
//
//    @Test
//    public void addStoneToShip() throws Exception {
//        Game game = new Game();
//        Round round = new Round();
//        User user = new User();
//        AShip ship = new OneSeatedShip();
//        AMove move = new AddStoneToShipMove(game,user,ship,0,round);
//        moveService.addStoneToShip(game,move);
//        assertNotNull(move);
//    }
//
//    @Test
//    public void addStoneToSiteBoard() throws Exception {
////        Long siteBoardId,String playerToken,Long gameId,Long shipId$
//
//    }
//
//    @Test
//    public void sailShip() throws Exception {
//
//    }
//
//    @Test
//    public void getStone() throws Exception {
//
//    }
//
//    @Test
//    public void findSiteboardsByType() throws Exception {
//
//    }
//
//}