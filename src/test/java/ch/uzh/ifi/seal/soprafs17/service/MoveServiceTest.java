package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by ilecipi on 10.04.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class MoveServiceTest {

    @Autowired
    GameService gameService;

    @Autowired
    UserService userService;

    @Autowired
    GameRepository gameRepository;

    private List<Game> games;

    @Test
    public void getStone() {
        User u = userService.createUser("testName", "testUsername", "t123", UserStatus.ONLINE, games);
        Game g = new Game();
        g.setName("firstGame");
        g.setOwner("testName");
        gameService.addGame(g,"t123");
        Assert.isNull(gameRepository.findByName("t123"));
    }
}
