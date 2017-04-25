package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest({"server.port=0"})
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private List<Game> games = new ArrayList<>();

    @Test
    public void createUser() {
        User user = userService.createUser("testName", "testUsername", "t123", UserStatus.OFFLINE, games);
        assertNotNull(userRepository.findByToken(user.getToken()));
        assertEquals(user.getName(),"testName");
        assertEquals(user.getUsername(),"testUsername");
        assertEquals(user.getToken(),"t123");
        assertEquals(user.getStatus(),UserStatus.OFFLINE);
        assertNotNull(user.getGames());
    }

    @Test
    public void deleteUser() {
        User user = userService.createUser("testName3", "testUsername3", "t1233", UserStatus.OFFLINE, games);
        userService.deleteUser(user.getId());
        Assert.assertNull(userService.getUser(user.getId()));
    }

    @Test
    public void login() throws Exception {
        User user = userService.createUser("testName1", "testUsername1", "t1231", UserStatus.OFFLINE, games);
        user = userService.login(user.getId());
        assertEquals(user.getStatus(),UserStatus.ONLINE);
    }

    @Test
    public void logout() throws Exception {
        User user = userService.createUser("testName2", "testUsername2", "t1232", UserStatus.ONLINE, games);
        user = userService.logout(user.getId(),user.getToken());
        assertEquals(user.getStatus(), UserStatus.OFFLINE);
    }


}
