package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.UserDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@SpringApplicationConfiguration(classes = Application.class)
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

    @Test
    public void listUsers() throws Exception {

    }

    @Test
    public void addUser() throws Exception {
        List<UserDTO> usersBefore = template.getForObject(base + "users", List.class);
        assertTrue(usersBefore.size() == 0);

        User userRequest = new User();
        HttpEntity<User> httpUserEntity = new HttpEntity<>(userRequest);

        // create user with no Name or Username
        ResponseEntity<String> responseUser = template.exchange(base + "users", HttpMethod.POST,httpUserEntity,String.class);
        assertEquals(null,responseUser.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,responseUser.getStatusCode());
    }

    @Test
    public void getUser() throws Exception {

    }

    @Test
    public void login() throws Exception {

    }

    @Test
    public void logout() throws Exception {

    }

}