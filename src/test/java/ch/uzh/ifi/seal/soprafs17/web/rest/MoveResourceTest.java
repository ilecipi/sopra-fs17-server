package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.MoveDTO;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.ShipDTO;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.UserDTO;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO.*;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.Sarcophagus;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.GetStoneMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.MoveRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.MoveService;
import ch.uzh.ifi.seal.soprafs17.service.UserService;
import org.hibernate.Hibernate;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.data.repository.init.ResourceReader.Type.JSON;

/**
 * Created by erion on 24.04.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@FixMethodOrder(MethodSorters.JVM)
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
    MoveService moveService;
    private Game game;
    private User owner;
    private User player;
    private List<Game> games = new ArrayList<>();

    @Value("${local.server.port}")
    private int port;
    private URL base;
    private RestTemplate template;
    private RestTemplate template2;

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


        ///games/1/start?playerToken=1
        responseGame = template.exchange(base + "games" +"/1" +"/start?playerToken=1", HttpMethod.POST, null, String.class);
//        URL obj = new URL(base + "games" + "/1" + "/start");
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//        con.setRequestMethod("POST");
//        String urlParameters = "playerToken=" + owner.getToken();
//
//        // Send post request
//        con.setDoOutput(true);
//        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//        wr.writeBytes(urlParameters);
//        wr.flush();
//        wr.close();
//        int responseCode = con.getResponseCode();
//        assertEquals(GameStatus.RUNNING, gameRepository.findOne(1L).getStatus());

    }

    @Test
    public void getMove() throws Exception {
//        AMove move = new GetStoneMove(this.owner, game.getCurrentRound(), this.game);
//        moveRepo.save(move);
//        assertEquals(move.getId(), moveRepo.findOne(move.getId()).getId());
//        MoveDTO moveDTO = template.getForObject(base + "/games/1/rounds/1", MoveDTO.class);
//        assertEquals(move.getId(), moveDTO.id);
    }


    @Test
    public void addStoneToShip() throws Exception {
        ResponseEntity<String> response = template.exchange(base + "games/1/rounds/1/ships/1?playerToken=" + player.getToken() +
                "&position=0", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        ResponseEntity<String>  response1 = template.exchange(base + "games/1/rounds/1/ships/1?playerToken=1&position=0", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.OK, response1.getStatusCode());

        response = template.exchange(base + "/games/1/rounds/1/ships/1?playerToken=" + owner.getToken() +
                "&position=0", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        response = template.exchange(base + "/games/1/rounds/1/ships/1?playerToken=" + player.getToken() +
                "&position=1", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        response = template.exchange(base + "/games/1/rounds/1/ships/1?playerToken=" + owner.getToken() +
                "&position=2", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        response = template.exchange(base + "/games/1/rounds/1/ships/2?playerToken=" + player.getToken() +
                "&position=0", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        response = template.exchange(base + "/games/1/rounds/1/ships/2?playerToken=" + owner.getToken() +
                "&position=1", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());


    }
    @Test
    public void sailShip() throws Exception {
        ShipDTO shipDTO = template.getForObject(base + "games/1/rounds/1/ships/1",ShipDTO.class);
        assertNotNull(shipDTO);

        List<ShipDTO> shipDTOList = template.getForObject(base + "games/1/rounds/1/ships",List.class);
        assertEquals(4,shipDTOList.size());
        this.addStoneToShip();
        ResponseEntity<String> response = template.exchange(base + "/games/1/rounds/1/ships/"+shipDTO.id+"/?siteBoardsType=temple&playerToken=1", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
//        ResponseEntity<TempleDTO> responseDTO = template.exchange(base + "games" +"/1/market", HttpMethod.GET, null, TempleDTO.class);
//        assertTrue(responseDTO.getBody().isOccupied);
    }


    @Test
    public void sailShipToMarket() throws Exception{
        this.addStoneToShip();
        ResponseEntity<String> response = template.exchange(base + "/games/1/rounds/1/ships/1/?siteBoardsType=market&playerToken=1", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
//        ResponseEntity<MarketDTO> responseDTO = template.exchange(base + "games" +"/1/market", HttpMethod.GET, null, MarketDTO.class);
//        assertTrue(responseDTO.getBody().isOccupied);
    }

    @Test
    public void sailShipToPyramid() throws Exception{
        this.addStoneToShip();
        ResponseEntity<String> response = template.exchange(base + "/games/1/rounds/1/ships/1/?siteBoardsType=pyramid&playerToken=1", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
//        ResponseEntity<PyramidDTO> responseDTO = template.exchange(base + "games" +"/1/market", HttpMethod.GET, null, PyramidDTO.class);
//        assertTrue(responseDTO.getBody().isOccupied);
    }

    @Test
    public void sailShipToBurialChamber() throws Exception{
        this.addStoneToShip();
        ResponseEntity<String> response = template.exchange(base + "/games/1/rounds/1/ships/1/?siteBoardsType=burialchamber&playerToken=1", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
//        ResponseEntity<BurialChamberDTO> responseDTO = template.exchange(base + "games" +"/1/market", HttpMethod.GET, null, BurialChamberDTO.class);
//        assertTrue(responseDTO.getBody().isOccupied);
    }

    @Test
    public void sailShipToObelisk() throws Exception{
        this.addStoneToShip();
        ResponseEntity<String> response = template.exchange(base + "/games/1/rounds/1/ships/1/?siteBoardsType=obelisk&playerToken=1", HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
//        ResponseEntity<ObeliskDTO> responseDTO = template.exchange(base + "games" +"/1/market", HttpMethod.GET, null, ObeliskDTO.class);
//        assertTrue(responseDTO.getBody().isOccupied);
    }




    @Test
    public void getStones() throws Exception {
        ResponseEntity<String> response = template.exchange(base + "/games/1/rounds/1/users?playerToken=1", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        response = template.exchange(base + "/games/1/rounds/1/users?playerToken=2", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        response = template.exchange(base + "/games/1/rounds/1/users?playerToken=1", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        response = template.exchange(base + "/games/1/rounds/1/users?playerToken=2", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void giveCardToUser() throws Exception {
        this.sailShipToMarket();
        ResponseEntity<String> response = template.exchange(base + "/games/1/rounds/1/market?playerToken=1&position=0", HttpMethod.POST, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

//        response = template.exchange(base + "/games/1/rounds/1/market?playerToken=2&position=1", HttpMethod.POST, null, String.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void playMarketCard() throws Exception {
    }

    @Test
    public void playLeverCard() throws Exception {

    }

}