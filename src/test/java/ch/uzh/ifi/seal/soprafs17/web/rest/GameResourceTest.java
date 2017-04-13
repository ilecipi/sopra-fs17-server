package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.GameDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs17.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static sun.audio.AudioPlayer.player;

/**
 * Created by ilecipi on 13.04.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GameResourceTest {
    @Autowired
    protected GameRepository gameRepository;
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private UserService userService;

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
    public void addGame() throws Exception {
        // list games with size = 0
        List<GameDTO> gamesBefore = template.getForObject(base + "games", List.class);
        Assert.assertTrue(gamesBefore.size() == 0);

        Game gameRequest = new Game();
        gameRequest.setName("testGame");
        HttpEntity<Game> httpGameEntity = new HttpEntity<Game>(gameRequest);

        String ownerToken = null;
        User userRequest = new User();
        userRequest.setName("Test");
        userRequest.setUsername("testUser1");
        HttpEntity<User> httpUserEntity = new HttpEntity<User>(userRequest);

        // add Game with no userToken or false user Token
        ResponseEntity<String> responseGame = template.exchange(base + "games?token=" + ownerToken, HttpMethod.POST, httpGameEntity, String.class);
        Assert.assertEquals(null, responseGame.getBody());

        //add User
        ResponseEntity<User> responseUser = template.exchange(base + "users", HttpMethod.POST, httpUserEntity, User.class);
        ownerToken = responseUser.getBody().getToken();

        // add Game with userToken
        responseGame = template.exchange(base + "games?token=" + ownerToken, HttpMethod.POST, httpGameEntity, String.class);
        JSONObject jsonResponse = new JSONObject(responseGame.getBody());
        Long gameId = Long.parseLong(jsonResponse.get("id").toString());
        Game game = gameRepository.findOne(gameId);
        Assert.assertEquals(game.getId().intValue(), Integer.parseInt(jsonResponse.get("id").toString()));
    }

    @Test
    public void getGames() throws Exception {
        List<GameDTO> gamesAfter = template.getForObject(base + "games", List.class);
        Assert.assertEquals(1, gamesAfter.size());
    }
    @Test
    public void getGame() throws Exception {
        GameDTO game = template.getForObject(base + "games" + "/1", GameDTO.class);
        Assert.assertNotNull(game);
    }


    @Test
    public void createPlayer() throws Exception {
        User userRequest2 = new User();
        userRequest2.setName("Test2");
        userRequest2.setUsername("testUser2");
        userRequest2.setToken("2");
        HttpEntity<User> httpUserEntity = new HttpEntity<User>(userRequest2);
        //Add second user to the game
        ResponseEntity<User> responseUser = template.exchange(base + "users", HttpMethod.POST, httpUserEntity, User.class);
        //http://localhost:8080/games/1/player?token=2
        ResponseEntity<String> responseGame = template.exchange(base + "games" +"/1" +"/player?token=" + userRequest2.getToken(), HttpMethod.POST, httpUserEntity, String.class);

        assertEquals(UserStatus.IN_A_LOBBY, userRepository.findByName("Test2").getStatus());
        System.out.println("AAAAAAAA"+userRepository.findByName("Test").getName());
    }

    @Test
    public void startGame() throws Exception {
//        System.out.println(gameRepository);
//        System.out.println("BBBBBBB"+userRepository.findByName("Test"));
//        Game game = gameRepository.findOne(1L);
////        assertNotNull(game);
//        User owner = userRepository.findByName(game.getOwner());
//
//
//
//        //http://localhost:8080/games/1/start
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
//
//        body.add("token", owner.getToken());
//        HttpEntity<User> httpUserEntity = new HttpEntity<User>(owner,body);
//        ResponseEntity<String> responseGame = template.exchange(base + "games" +"/1" +"/start", HttpMethod.POST, httpUserEntity, String.class);
//        game = gameRepository.findOne(1L);
////        assertEquals(GameStatus.RUNNING, game.getStatus());
//        System.out.println(game.getStatus());

    }

    @Test
    public void stopGame() throws Exception {

    }

    @Test
    public void listPlayers() throws Exception {

    }

    @Test
    public void addUser() throws Exception {

    }

    @Test
    public void getPlayer() throws Exception {

    }



}