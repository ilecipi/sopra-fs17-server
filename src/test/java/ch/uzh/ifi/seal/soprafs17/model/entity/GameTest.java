package ch.uzh.ifi.seal.soprafs17.model.entity;

import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by erion on 11.04.17.
 */
public class GameTest {

    @Test
    public void getRounds() throws Exception {
        Game game=new Game();
        Round firstRound=new Round();
        List<Round> rounds=new ArrayList<>();
        rounds.add(firstRound);
        game.setRounds(rounds);
        assertNotNull(game.getRounds());
    }

    @Test
    public void setRounds() throws Exception {
        Game game = new Game();
        Round round = new Round();
        List<Round> rounds = new ArrayList<>();
        game.setRounds(rounds);
        assertEquals(rounds,game.getRounds());
    }


    @Test
    public void initShipsCards() throws Exception {
        Game game = new Game();
        int numberOfPlayer = 4;
        for (int i = 0; i < numberOfPlayer; i++) {
            User user = new User();
            user.setUsername("User " + i);
            game.getPlayers().add(user);
        }
        game.initShipsCards();
        assertNotNull(game.getShipsCards());
    }

    @Test
    public void initMarketCards() throws Exception {
        Game game = new Game();
        game.initMarketCards();
        assertNotNull(game.getMarketCards());
    }
    @Test
    public void findNextPlayer() throws Exception {

        int numberOfPlayer = 4;
        Game game = new Game();
        for (int i = 0; i < numberOfPlayer; i++) {
            User user = new User();
            user.setUsername("User " + i);
            game.getPlayers().add(user);
        }
        User firstPlayer = game.getPlayers().get(0);
        game.setCurrentPlayer(firstPlayer);
        User nextPlayer = game.findNextPlayer();
        assertEquals(game.getPlayers().indexOf(nextPlayer), game.getPlayers().indexOf(firstPlayer)+1);
    }

}