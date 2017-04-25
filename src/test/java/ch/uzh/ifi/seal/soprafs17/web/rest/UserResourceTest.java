package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.UserDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by erion on 13.04.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserResourceTest {


    @Autowired
    UserRepository userRepo;
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
    @SuppressWarnings("unchecked")
    public void UserResourceTestForAll() throws Exception {
        this.addUser();
        this.listUsers();
        this.getUser();
        this.logout();
    }

    public void addUser() throws Exception {

        User userRequest2 = new User();

        HttpEntity<User> httpUserEntity2 = new HttpEntity<>(userRequest2);

        // create user with no Name or Username
        ResponseEntity<String> responseMessage = template.exchange(base + "users", HttpMethod.POST, httpUserEntity2, String.class);
        assertEquals(null, responseMessage.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseMessage.getStatusCode());

        //add user with Name and Username
        userRequest2.setName("testName123");
        userRequest2.setUsername("testUsername123");

        httpUserEntity2 = new HttpEntity<>(userRequest2);

        responseMessage = template.exchange(base + "users", HttpMethod.POST, httpUserEntity2, String.class);
        assertEquals(HttpStatus.CREATED, responseMessage.getStatusCode());
        List<UserDTO> usersBefore = template.getForObject(base + "users", List.class);
        assertNotNull(usersBefore);

        User userRequest1 = new User();
        //add user with Name and Username
        userRequest1.setName("testName1234");
        userRequest1.setUsername("testUsername1234");

        HttpEntity<User> httpUserEntity = new HttpEntity<>(userRequest1);

        template.exchange(base + "users", HttpMethod.POST, httpUserEntity, String.class);
    }


    public void listUsers() throws Exception {
        List<UserDTO> usersAfter = template.getForObject(base + "users", List.class);
        assertEquals(2, usersAfter.size());
    }


    public void getUser() throws Exception {
        ResponseEntity<UserDTO> userAfter = template.getForEntity(base + "users/2", UserDTO.class);
        assertNotNull(userAfter);
        assertEquals("testName123", userAfter.getBody().name);
        assertEquals("testUsername123", userAfter.getBody().username);

    }

    public void logout() throws Exception {
        ResponseEntity<UserDTO> userAfter = template.getForEntity(base + "users/2", UserDTO.class);
        assertEquals(UserStatus.ONLINE, userAfter.getBody().status);


        ResponseEntity<String> responseMessage = template.exchange(base + "users/2/logout?token=" + userAfter.getBody().token, HttpMethod.PUT, userAfter, String.class);
        userAfter = template.getForEntity(base + "users/2", UserDTO.class);
        assertEquals(UserStatus.OFFLINE, userAfter.getBody().status);

    }

}
