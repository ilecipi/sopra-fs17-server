package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.UserDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.minidev.json.JSONObject;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by erion on 13.04.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class UserResourceTest {


    @Value("${local.server.port}")
    private int port;
    private URL base;
    private RestTemplate template;


    @Before
    public void setUp() throws MalformedURLException {
        this.base = new URL("http://localhost:" + port + "/");
        this.template = new TestRestTemplate();
    }

    @Autowired
    UserRepository userRepo;
    @Test
    @SuppressWarnings("unchecked")
    public void addUser() throws Exception {

        User userRequest3 = new User();

        HttpEntity<User> httpUserEntity = new HttpEntity<>(userRequest3);

        // create user with no Name or Username
        ResponseEntity<String> responseMessage = template.exchange(base + "users", HttpMethod.POST,httpUserEntity,String.class);
        assertEquals(null,responseMessage.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,responseMessage.getStatusCode());

        //add user with Name and Username
        userRequest3.setName("testName123");
        userRequest3.setUsername("testUsername123");

        HttpEntity<User> httpUserEntity2 = new HttpEntity<>(userRequest3);

        responseMessage = template.exchange(base + "users", HttpMethod.POST,httpUserEntity2,String.class);
        assertEquals(HttpStatus.CREATED,responseMessage.getStatusCode());
        List<UserDTO> usersBefore = template.getForObject(base + "users", List.class);
        assertNotNull(usersBefore);
    }

    @Test
    public void listUsers() throws Exception {
        List<UserDTO> usersAfter = template.getForObject(base + "users", List.class);
        assertEquals(2,usersAfter.size());
    }


    @Test
    public void getUser() throws Exception {
        ResponseEntity<UserDTO>  userAfter = template.getForEntity(base + "users/4" , UserDTO.class);
        assertEquals("testName123",userAfter.getBody().name);
        assertEquals("testUsername123",userAfter.getBody().username);

    }

    @Test
    public void logout() throws Exception {
        ResponseEntity<UserDTO>  userAfter = template.getForEntity(base + "users/4" , UserDTO.class);
        assertEquals(UserStatus.ONLINE,userAfter.getBody().status);


        ResponseEntity<String> responseMessage = template.exchange(base + "users/4/logout?token="+userAfter.getBody().token,HttpMethod.PUT,userAfter,String.class);
        userAfter = template.getForEntity(base + "users/4" , UserDTO.class);
        assertEquals(UserStatus.OFFLINE,userAfter.getBody().status);

    }

}
