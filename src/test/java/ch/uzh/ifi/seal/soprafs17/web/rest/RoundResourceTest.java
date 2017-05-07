package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.RoundDTO;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.UserDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.RoundService;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.web.client.RestTemplate;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by tonio99tv on 20/04/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class RoundResourceTest {

    @Autowired
    protected GameRepository gameRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private GameResource gameResource;

    @Autowired
    private RoundResource roundResource;

    @Autowired
    private GameService gameService;

    @Value("${local.server.port}")
    private int port;

    private URL base;
    private RestTemplate template;


    @Before
    public void setUp() throws MalformedURLException {
        this.base = new URL("http://localhost:" + port + "/");
        this.template = new TestRestTemplate();
    }

    @Test
    public void roundResourceTestForAll() throws Exception{
        this.getSpecificRoundBeforeInstaniating();
        this.listRoundsBeforeInstantiating();
        this.instantiateGame();
        this.getSpecificRound();
        this.listRounds();

    }


    public void getSpecificRoundBeforeInstaniating() throws Exception{
        RoundDTO round = template.getForObject(base + "games" + "/1" + "/rounds" + "/1",RoundDTO.class);
        assertNotNull(round);
        assertEquals(null, round.id);

    }

    public void listRoundsBeforeInstantiating() throws Exception{
        List<RoundDTO> roundsAfter = template.getForObject(base + "games"+"/1"+"/rounds", List.class);
        assertNotNull(roundsAfter);
        Assert.assertEquals(0, roundsAfter.size());
    }

    public void instantiateGame() throws  Exception {
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

        //Start the game
        game = gameRepository.findOne(1L);
        Assert.assertNotNull(game);
        owner = userRepository.findByUsername(game.getOwner());

        URL obj = new URL(base + "games" + "/1" + "/start");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        String urlParameters = "playerToken=" + owner.getToken();

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        assertEquals(GameStatus.RUNNING, gameRepository.findOne(1L).getStatus());
    }

    public void getSpecificRound() throws Exception{
        //get a specific round with an http request
        RoundDTO round = template.getForObject(base + "games" + "/1" + "/rounds" + "/1",RoundDTO.class);
        assertNotNull(round);
        assertEquals(""+1L, ""+round.id);

    }

    public void listRounds() throws Exception{
        List<RoundDTO> roundsAfter = template.getForObject(base + "games"+"/1"+"/rounds", List.class);
        assertNotNull(roundsAfter);
        //check that a round has been initialized, i.e the firstone
        Assert.assertEquals(1, roundsAfter.size());
    }


}
