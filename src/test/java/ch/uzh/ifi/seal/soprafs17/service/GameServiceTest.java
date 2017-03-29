//package ch.uzh.ifi.seal.soprafs17.service;
//
//import ch.uzh.ifi.seal.soprafs17.Application;
//import ch.uzh.ifi.seal.soprafs17.GameConstants;
//import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
//import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
//import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
//import ch.uzh.ifi.seal.soprafs17.model.entity.moves.Move;
//import ch.uzh.ifi.seal.soprafs17.model.entity.User;
//import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
//import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.Assert.assertNotNull;
//
//
///**
// * Created by liwitz 29.03.17
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@Transactional
//public class GameServiceTest {
//
//    @Autowired
//    private UserRepository userRepo;
//
//    @Autowired
//    private GameRepository gameRepo;
//
//    @Autowired
//    private SiteBoardsService siteBoardsService;
//
//    @Autowired
//    private ShipService shipService;
//
//    @Autowired
//    private RoundService roundService;
//
//    @Autowired
//    private GameService gameService;
//
//    private List<Game> games;
//
//    @Autowired
//    private Game game;
//
//    public String addGame(game, String userToken){
//        Assert.assertNull(gameRepo.findByName("DummyUser"));
//        String gameStr = gameService.addGame(game, "t123");
//        Assert.assertNotNull(gameStr);
//    }
//
//
//}
