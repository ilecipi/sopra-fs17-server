package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.MoveDTO;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.ShipDTO;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.UserDTO;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO.*;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.Sarcophagus;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.GetStoneMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.MoveService;
import ch.uzh.ifi.seal.soprafs17.service.SiteBoardsService;
import ch.uzh.ifi.seal.soprafs17.service.UserService;
import ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception.*;
import org.hibernate.Hibernate;
import org.json.JSONObject;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import static org.junit.Assert.*;
import static org.springframework.data.repository.init.ResourceReader.Type.JSON;

/**
 * Created by erion on 24.04.17.
 *This class tests all the possible move applicable to the game. It covers the cases in which the
 * game is running until the game is over
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@FixMethodOrder(MethodSorters.JVM)
/**
*
* */
public class MoveResourceTest {

    @Autowired
    MoveRepository moveRepo;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    UserService userService;
    @Autowired
    GameService gameService;

    @Autowired
    SiteBoardsService siteBoardsService;

    @Autowired
    MoveService moveService;
    @Autowired
    MarketCardRepository marketCardRepository;
    @Autowired
    RoundRepository roundRepository;
    private Game game;
    private User owner;
    private User player;
    private List<Game> games = new ArrayList<>();

    @Value("${local.server.port}")
    private int port;
    private URL base;
    private RestTemplate template;
    private RestTemplate template2;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    //Run this method before executing each method
    @Before
    public void beforeEach() throws IOException {

        this.base = new URL("http://localhost:" + port + "/");
        this.template = new TestRestTemplate();
        //create game
        Game gameRequest = new Game();
        gameRequest.setName("testGame");
        HttpEntity<Game> httpGameEntity = new HttpEntity<>(gameRequest);

        //Create User1
        String userToken1 = null;
        User userRequest1 = new User();
        userRequest1.setName("Test1");
        userRequest1.setUsername("testUser1");
        HttpEntity<User> httpUserEntity1 = new HttpEntity<User>(userRequest1);

        //add User1
        ResponseEntity<UserDTO> responseUser1 = template.exchange(base + "users", HttpMethod.POST, httpUserEntity1, UserDTO.class);
        userToken1 = responseUser1.getBody().token;

        //Create User2
        String userToken2 = null;
        User userRequest2 = new User();
        userRequest2.setName("Test2");
        userRequest2.setUsername("testUser2");
        HttpEntity<User> httpUserEntity2 = new HttpEntity<User>(userRequest2);

        //add User2
        ResponseEntity<UserDTO> responseUser2 = template.exchange(base + "users", HttpMethod.POST, httpUserEntity2, UserDTO.class);
        userToken2 = responseUser2.getBody().token;

        // add Game with userToken
        ResponseEntity<String> responseGame = template.exchange(base + "games?token=" + userToken1, HttpMethod.POST, httpGameEntity, String.class);
        JSONObject jsonResponse = new JSONObject(responseGame.getBody());
        Long gameId = Long.parseLong(jsonResponse.get("id").toString());
        Game game = gameRepository.findOne(gameId);
        //Check that the game is created
        User user1 = userRepository.findByToken(userToken1);
        assertEquals(UserStatus.IN_A_LOBBY, userRepository.findByName("Test1").getStatus());

        //Make the second player join the game
        userRequest2.setToken("2");
        HttpEntity<User> httpUserEntity = new HttpEntity<User>(userRequest2);
        responseGame = template.exchange(base + "games" +"/1" +"/player?token=" + userRequest2.getToken(), HttpMethod.POST, httpUserEntity2, String.class);
        assertEquals(UserStatus.IN_A_LOBBY, userRepository.findByName("Test2").getStatus());

        //Owner is ready
        User owner = userRepository.findByToken(user1.getToken());
        HttpEntity<User> HttpOwnerEntity = new HttpEntity<>(owner);
        responseGame = template.exchange(base + "games" +"/1" +"?token=" + owner.getToken(), HttpMethod.PUT, HttpOwnerEntity, String.class);
        assertEquals(UserStatus.IS_READY,userRepository.findByToken(owner.getToken()).getStatus());

        //Second player is ready
        responseGame = template.exchange(base + "games" +"/1" +"?token=" + userRequest2.getToken(), HttpMethod.PUT, httpUserEntity, String.class);

        assertEquals(UserStatus.IS_READY,userRepository.findByToken(userRequest2.getToken()).getStatus());
        this.player = userRepository.findByToken(userToken2);
        this.owner = userRepository.findByToken(userToken1);
        //Start the game
        game = gameRepository.findOne(1L);
        assertNotNull(game);
        owner = userRepository.findByUsername(game.getOwner());


        ///Start the game
        responseGame = template.exchange(base + "games" +"/1" +"/start?playerToken=1", HttpMethod.POST, null, String.class);

    }

//    @Test
//    public void getMove() throws Exception {
//        AMove move = new GetStoneMove(this.owner, game.getCurrentRound(), this.game);
//        moveRepo.save(move);
//        assertEquals(move.getId(), moveRepo.findOne(move.getId()).getId());
//        MoveDTO moveDTO = template.getForObject(base + "/games/1/rounds/1", MoveDTO.class);
//        assertEquals(move.getId(), moveDTO.id);
//    }


    @Test
    public void addStoneToShip() throws Exception {

        //Not the current player tries to add a stone
        ResponseEntity<String> response = template.exchange(base + "games/1/rounds/1/ships/1?playerToken=" + player.getToken() +
                "&position=0", HttpMethod.POST, null, String.class);

        //The call is not accepted
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        //The current player performs the move
        ResponseEntity<String>  response1 = template.exchange(base + "games/1/rounds/1/ships/1?playerToken="+owner.getToken()+"&position=0", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.OK, response1.getStatusCode());

        //The old current player tries to perform the same move but he cannot
        response = template.exchange(base + "/games/1/rounds/1/ships/1?playerToken=" + owner.getToken() +
                "&position=0", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        //The new current player (player 2) performs the move
        response = template.exchange(base + "/games/1/rounds/1/ships/1?playerToken=" + player.getToken() +
                "&position=1", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        response = template.exchange(base + "/games/1/rounds/1/ships/1?playerToken=" + owner.getToken() +
                "&position=2", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        response = template.exchange(base + "/games/1/rounds/1/ships/2?playerToken=" + player.getToken() +
                "&position=0", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        //The current player tries to put a stone but he does not have any stones
        response = template.exchange(base + "/games/1/rounds/1/ships/2?playerToken=" + owner.getToken() +
                "&position=1", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());


    }

    @Test
    public void addStoneToShipNotEnoughStones() throws Exception {
        this.addStoneToShip();
        exception.expect(NotEnoughStoneException.class);

        //throw an exception because the player does not have any stones
        moveService.addStoneToShip(1L,owner.getToken(),1L,1L,0);
    }

    @Test
    public void addStoneToShipUnavailableShipException() throws Exception {
        this.addStoneToShip();
        exception.expect(UnavailableShipPlaceException.class);
        moveService.getStone(1L,1L,"1");
        moveService.getStone(1L,1L,"2");

        //this method throws an exception, because the player tries to put a stone on a already occupied position
        moveService.addStoneToShip(1L,owner.getToken(),1L,1L,0);
    }
    @Test
    public void sailShip() throws Exception {
        //get the ship
        ShipDTO shipDTO = template.getForObject(base + "games/1/rounds/1/ships/1",ShipDTO.class);
        assertNotNull(shipDTO);

        List<ShipDTO> shipDTOList = template.getForObject(base + "games/1/rounds/1/ships",List.class);

        //check that the current round has only 4 ships and not more
        assertEquals(4,shipDTOList.size());
        this.addStoneToShip();

        //sail the ship to the temple
        ResponseEntity<String> response = template.exchange(base + "/games/1/rounds/1/ships/"+shipDTO.id+"/?siteBoardsType=temple&playerToken=1", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        //check that now the Temple is occupied
        assertTrue(siteBoardsService.getTemple(1L).isOccupied());
    }


    @Test
    public void sailShipToMarket() throws Exception{
        this.addStoneToShip();
        ResponseEntity<String> response = template.exchange(base + "/games/1/rounds/1/ships/1/?siteBoardsType=market&playerToken=1", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        //check that now the Market is occupied
        assertTrue(siteBoardsService.getMarket(1L).isOccupied());
    }

    @Test
    public void sailShipToPyramid() throws Exception{
        this.addStoneToShip();
        ResponseEntity<String> response = template.exchange(base + "/games/1/rounds/1/ships/1/?siteBoardsType=pyramid&playerToken=1", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        //check that now the Pyramid is occupied
        assertTrue(siteBoardsService.getPyramid(1L).isOccupied());
    }

    @Test
    public void sailShipToBurialChamber() throws Exception{
        this.addStoneToShip();
        assertFalse(siteBoardsService.getBurialChamber(1L).isOccupied());
        ResponseEntity<String> response = template.exchange(base + "/games/1/rounds/1/ships/1/?siteBoardsType=burialchamber&playerToken=1", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(siteBoardsService.getBurialChamber(1L).isOccupied());
    }

    @Test
    public void sailShipToBurialChamberExceptionOccupied() throws Exception{
        gameService.refillShip(1L);
        moveService.sailShip(1L,1L,1L,"1","burialchamber");

        exception.expect(SiteBoardIsOccupiedException.class);
        moveService.sailShip(1L,1L,2L,"2","burialchamber");

    }

    @Test
    public void sailShipToObelisk() throws Exception{
        this.addStoneToShip();
        ResponseEntity<String> response = template.exchange(base + "/games/1/rounds/1/ships/1/?siteBoardsType=obelisk&playerToken=1", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        exception.expect(DockedShipException.class);
        moveService.sailShip(1L,1L,1L,"2","obelisk");

        //check that now the Obelisk is occupied
        assertTrue(siteBoardsService.getObelisk(1L).isOccupied());
    }




    @Test
    public void getStones() throws Exception {
        //The current player can retrieve stones from the quarry
        ResponseEntity<String> response = template.exchange(base + "/games/1/rounds/1/users?playerToken=1", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        response = template.exchange(base + "/games/1/rounds/1/users?playerToken=2", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        //The current player tries to retieve stones from the quarry but his supply sled is already full
        response = template.exchange(base + "/games/1/rounds/1/users?playerToken=1", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        //The current player tries to retieve stones from the quarry but his supply sled is already full
        response = template.exchange(base + "/games/1/rounds/1/users?playerToken=2", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void giveCardToUser() throws Exception {
        //this method is only used for test purposes, i.e. for testing the cards
        this.sailShipToMarket();
        ResponseEntity<String> response = template.exchange(base + "/games/1/rounds/1/market?playerToken=1&position=0", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void playSarcophagusCard() throws Exception {
        //this method is only used for test purposes, i.e. for testing the cards
        ResponseEntity<String> response = template.exchange(base + "/games/1/giveCardsTest", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        //change the state of the game such that an immediateCard has to be played, nothing else is allowed
        roundRepository.findById(1L).setImmediateCard(true);

        //check that the card hasn't been played yet
        assertFalse(marketCardRepository.findById(5L).isPlayed());

        //play the card
        moveService.playMarketCard(1L,1L,"1",5L);

        //check that the card has really been played
        assertTrue(marketCardRepository.findById(5L).isPlayed());

        //the player that used the card is no longer the current player
        exception.expect(NotCurrentPlayerException.class);
        moveService.addStoneToShip(1L,"2",1L,1L,0);
    }

    @Test(expected = ImmediateCardNotPlayedException.class)
    public void isImmediateCardPlayed() throws Exception{
        Round round = roundRepository.findById(1L);
        round.setImmediateCard(true);
        roundRepository.save(round);

        //check that nothing else beside playing an immediate card is allowed
        moveService.addStoneToShip(1L,"1",1L,1L,0);
    }

    @Test
    public void playPavedPath() throws Exception {
        ResponseEntity<String> response = template.exchange(base + "/games/1/giveCardsTest", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        assertFalse(marketCardRepository.findById(6L).isPlayed());
        moveService.playMarketCard(1L,1L,"1",6L);
        assertTrue(marketCardRepository.findById(6L).isPlayed());
    }

    @Test
    public void playEntranceCard() throws Exception {
        ResponseEntity<String> response = template.exchange(base + "/games/1/giveCardsTest", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        assertTrue(!marketCardRepository.findById(7L).isPlayed());
        moveService.playMarketCard(1L,1L,"1",7L);
        assertTrue(marketCardRepository.findById(7L).isPlayed());
    }

    @Test
    public void playChiselCard() throws Exception {
        ResponseEntity<String> response = template.exchange(base + "/games/1/giveCardsTest", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        assertFalse(marketCardRepository.findById(8L).isPlayed());

        //check all the stages for playing the Chisel card, i.e. add two stones
        moveService.playMarketCard(1L,1L,"1",8L);
        moveService.addStoneToShip(1L,"1",1L,1L,0);
        moveService.addStoneToShip(1L,"1",2L,1L,0);
        assertTrue(marketCardRepository.findById(8L).isPlayed());
    }

    @Test
    public void playHammerCard() throws Exception {
        ResponseEntity<String> response = template.exchange(base + "/games/1/giveCardsTest", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        assertFalse(marketCardRepository.findById(9L).isPlayed());
        int currentSupplySled = gameService.getPlayer(1L,1).getSupplySled();

        //check all the stages for playing the Chisel card, i.e. get maximum 3 stones from the quarry and put a stone on a ship

        moveService.playMarketCard(1L,1L,"1",9L);
        moveService.addStoneToShip(1L,"1",1L,1L,0);
        assertTrue(marketCardRepository.findById(9L).isPlayed());
    }

    @Test
    public void playLeverCard() throws Exception {
        ResponseEntity<String> response = template.exchange(base + "/games/1/giveCardsTest", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        gameService.refillShip(1L);
        assertFalse(marketCardRepository.findById(10L).isPlayed());
        moveService.playMarketCard(1L,1L,"1",10L);

        //check the stages for the lever card
        moveService.sailShip(1L,1L,2L,"1","pyramid");
        List<String> colors = new ArrayList<>();
        for(User u : gameService.getGame(1L).getPlayers()){
            colors.add(u.getColor());
        }
        moveService.playLeverCard(1L,1L,"1",colors);
        assertTrue(marketCardRepository.findById(10L).isPlayed());
    }

    @Test
    public void playLeverCardToMarket() throws Exception {
        //Play the lever card but on the market, important because the implementation is quite different
        ResponseEntity<String> response = template.exchange(base + "/games/1/giveCardsTest", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        gameService.refillShip(1L);
        assertFalse(marketCardRepository.findById(10L).isPlayed());
        moveService.playMarketCard(1L,1L,"1",10L);
        moveService.sailShip(1L,1L,2L,"1","market");

        //rearrange the colors of the stones
        List<String> colors = new ArrayList<>();
        for(User u : gameService.getGame(1L).getPlayers()){
            colors.add(u.getColor());
        }
        moveService.playLeverCard(1L,1L,"1",colors);
        assertTrue(marketCardRepository.findById(10L).isPlayed());
    }

    @Test
    public void playSailCard() throws Exception {
        ResponseEntity<String> response = template.exchange(base + "/games/1/giveCardsTest", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        assertFalse(marketCardRepository.findById(11L).isPlayed());
        moveService.playMarketCard(1L,1L,"1",11L);

        //check the stages for playing the Sail card, i.e add a stone and sail a ship
        moveService.addStoneToShip(1L,"1",4L,1L,0);
        moveService.sailShip(1L,1L,4L,"1","pyramid");
        assertTrue(marketCardRepository.findById(11L).isPlayed());
    }

    @Test(expected = GameFinishedException.class)
    public void GameIsOver() throws Exception{
        //Check that once the game is finished, no more move can be done
        gameService.fastForward(1L,6);
        gameService.fastForward(1L,6);
        gameService.fastForward(1L,6);
        gameService.fastForward(1L,6);
        gameService.fastForward(1L,6);
        gameService.fastForward(1L,6);
        assertEquals(GameStatus.FINISHED, gameRepository.findOne(1L).getStatus());

//        exception.expect(GameFinishedException.class);
        moveService.getStone(1L,6L,"1");

    }
}