//package ch.uzh.ifi.seal.soprafs17.service;
//
///**
// * Created by ilecipi on 25.04.17.
// */
//
//
//
//import static org.junit.Assert.assertEquals;
//import ch.uzh.ifi.seal.soprafs17.Application;
//import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
//import ch.uzh.ifi.seal.soprafs17.model.DTOs.MoveDTO;
//import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
//import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
//import ch.uzh.ifi.seal.soprafs17.model.entity.User;
//import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.*;
//import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
//import ch.uzh.ifi.seal.soprafs17.model.entity.moves.GetStoneMove;
//import ch.uzh.ifi.seal.soprafs17.model.entity.moves.PlayMarketCardMove;
//import ch.uzh.ifi.seal.soprafs17.model.entity.ships.OneSeatedShip;
//import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.*;
//import ch.uzh.ifi.seal.soprafs17.model.repository.*;
//import ch.uzh.ifi.seal.soprafs17.service.GameService;
//import ch.uzh.ifi.seal.soprafs17.service.MoveService;
//import ch.uzh.ifi.seal.soprafs17.service.RoundService;
//import ch.uzh.ifi.seal.soprafs17.service.UserService;
//import ch.uzh.ifi.seal.soprafs17.service.ruleEngine.RuleBook;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.IntegrationTest;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.boot.test.TestRestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Created by erion on 24.04.17.
// */
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@FixMethodOrder(MethodSorters.JVM)
//@Transactional
//public class MarketCardTest{
//
//    @Autowired
//    MoveRepository moveRepo;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    GameRepository gameRepository;
//
//    @Autowired
//    ShipRepository shipRepository;
//
//    @Autowired
//    MarketCardRepository marketCardRepository;
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    MoveService moveService;
//    @Autowired
//    GameService gameService;
//
//    @Autowired
//    RoundRepository roundRepository;
//
//    @Autowired
//    SiteBoardRepository siteBoardRepository;
//
//    @Autowired
//    RoundService roundService;
//
//    private Game game;
//    private User owner;
//    private User player;
//    Round round = new Round();
//    private List<Game> games = new ArrayList<>();
//
//
//
//    @Before
//    public void beforeEach() throws MalformedURLException {
//
//        this.game = new Game();
//        this.game.setOwner("testUsername");
//        this.game.setName("Game1");
//        this.game = gameRepository.save(game);
//        this.owner = new User();
//        this.player = new User();
//
//
//        owner = userService.createUser("testName", "testUsername", "t123", UserStatus.ONLINE, games);
//        player = userService.createUser("testName2", "testUsername2", "t1234", UserStatus.ONLINE, games);
//        owner.setColor("white");
//        owner.setColor("brown");
//
//        this.game.setPlayers(new ArrayList<>());
//        this.game.getPlayers().add(owner);
//        this.game.getPlayers().add(player);
//        this.game.setCurrentPlayer(owner);
//        this.game.setId(1L);
//        Round round = new Round();
//        round.setId(1L);
//        round.setGame(game);
//        round = roundRepository.save(round);
//
//        System.out.println(round.getId());
//        this.game.setRounds(new ArrayList<>());
//        this.game.getRounds().add(round);
//        this.game.setSiteBoards(new ArrayList<>());
//        this.round.setShips(new ArrayList<>());
//        this.round.getShips().add(shipRepository.save(new OneSeatedShip()));
//        this.round.getShips().add(shipRepository.save(new OneSeatedShip()));
//        this.round.getShips().add(shipRepository.save(new OneSeatedShip()));
//        this.round.getShips().add(shipRepository.save(new OneSeatedShip()));
//        this.game.getSiteBoards().add(siteBoardRepository.save(new Temple()));
//        this.game.getSiteBoards().add(siteBoardRepository.save(new Obelisk()));
//        this.game.getSiteBoards().add(siteBoardRepository.save(new Pyramid()));
//        this.game.getSiteBoards().add(siteBoardRepository.save(new Market()));
//        this.game.getSiteBoards().add(siteBoardRepository.save(new BurialChamber()));
//        this.game = gameRepository.save(game);
//        this.owner = userRepository.save(this.owner);
//        this.player = userRepository.save(this.player);
//        round = roundRepository.save(round);
//        game = gameRepository.save(game);
//        RuleBook ruleBook = new RuleBook();
//
//
//
//    }
//
//    @Test
//    public void testMarketCards() throws Exception {
//        this.owner.setMarketCards(new ArrayList<>());
//        AMarketCard sarco = new Sarcophagus();
//        sarco.setUser(this.owner);
//        marketCardRepository.save(sarco);
//        this.owner.getMarketCards().add(sarco);
//        this.owner = userRepository.save(this.owner);
////        PlayMarketCardMove move = new PlayMarketCardMove(owner, round, game, marketCardRepository.save(new Sarcophagus()));
////        move.makeMove(game);
////        //TODO: assert
////        move = new PlayMarketCardMove(owner, round, game, marketCardRepository.save(new Chisel()));
////        move.makeMove(game);
////        move = new PlayMarketCardMove(owner, round, game, marketCardRepository.save(new Entrance()));
////        move.makeMove(game);
////        move = new PlayMarketCardMove(owner, round, game, marketCardRepository.save(new Hammer()));
////        move.makeMove(game);
////        move = new PlayMarketCardMove(owner, round, game, marketCardRepository.save(new Lever()));
////        move.makeMove(game);
////        move = new PlayMarketCardMove(owner, round, game, marketCardRepository.save(new ObeliskDecoration()));
////        move.makeMove(game);
////        move = new PlayMarketCardMove(owner, round, game, marketCardRepository.save(new PavedPath()));
////        move.makeMove(game);
////        move = new PlayMarketCardMove(owner, round, game, marketCardRepository.save(new Sail()));
////        move.makeMove(game);
////        move = new PlayMarketCardMove(owner, round, game, marketCardRepository.save(new Statue()));
////        move.makeMove(game);
////        move = new PlayMarketCardMove(owner, round, game, marketCardRepository.save(new TempleDecoration()));
////        move.makeMove(game);
////        move = new PlayMarketCardMove(owner, round, game, marketCardRepository.save(new BurialChamberDecoration()));
////        move.makeMove(game);
//
//
//    }
//}