package ch.uzh.ifi.seal.soprafs17.model.entity;

import ch.uzh.ifi.seal.soprafs17.service.GameService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by erion on 11.04.17.
 */
public class GameTest {

    @Autowired
    GameService gameService;

    @Test
    public void getPoints() throws Exception {

    }

    @Test
    public void setPoints() throws Exception {

    }


    @Test
    public void getShipsCards() throws Exception {
        Game game = new Game();
        game.initShipsCards();
        assertNotNull(game.getShipsCards());
    }

    @Test
    public void getMarketCards() throws Exception {
        Game game = new Game();
        game.initMarketCards();
        assertNotNull(game.getMarketCards());
    }

    @Test
    public void setMarketCards() throws Exception {
        Game game = new Game();
        Map<Integer, String> marketCardsTest = new HashMap<>();
        game.setMarketCards(marketCardsTest);
        assertSame(game.getMarketCards(), marketCardsTest);
    }

    @Test
    public void getSiteBoards() throws Exception {

    }

    @Test
    public void setSiteBoards() throws Exception {
        Game game = new Game();
        game.setSiteBoards(new ArrayList<>());
        assertNotNull(game.getSiteBoards());
    }

    @Test
    public void getId() throws Exception {

    }

    @Test
    public void setId() throws Exception {
        Game game = new Game();
        game.setId(new Long(1));
        assertNotNull(game.getId());
    }

    @Test
    public void getName() throws Exception {

    }

    @Test
    public void setName() throws Exception {
        Game game = new Game();
        game.setName("test");
        assertEquals("test", game.getName());
    }

    @Test
    public void getOwner() throws Exception {

    }

    @Test
    public void setOwner() throws Exception {
        Game game = new Game();
        game.setOwner("testOwner");
        assertEquals("testOwner", game.getOwner()
        );
    }

    @Test
    public void getPlayers() throws Exception {

    }

    @Test
    public void setPlayers() throws Exception {

    }

    @Test
    public void getStatus() throws Exception {

    }

    @Test
    public void setStatus() throws Exception {

    }

    @Test
    public void getCurrentPlayer() throws Exception {

    }

    @Test
    public void setCurrentPlayer() throws Exception {

    }

    @Test
    public void setNextPlayer() throws Exception {

    }

    @Test
    public void getNextPlayer() throws Exception {

    }


    @Test
    public void getColors() throws Exception {

    }

    @Test
    public void setColors() throws Exception {

    }

    @Test
    public void getRounds() throws Exception {
        Game game = new Game();
        Round firstRound = new Round();
        List<Round> rounds = new ArrayList<>();
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
        assertEquals(rounds, game.getRounds());
    }


    @Test
    public void initShipsCards() throws Exception {
        Game game = new Game();
        int numberOfPlayer = 4;
        for (int i = 0; i < numberOfPlayer; i++) {
            User user = new User();
            game.getPlayers().add(user);
        }
        game.initShipsCards();
        assertNotNull(game.getShipsCards());

        Game game1 = new Game();
        int numberOfPlayer1 = 3;
        for (int i = 0; i < numberOfPlayer1; i++) {
            User user = new User();
            game1.getPlayers().add(user);
        }
        game1.initShipsCards();
        assertNotNull(game1.getShipsCards());

        Game game2 = new Game();
        int numberOfPlayer2 = 2;
        for (int i = 0; i < numberOfPlayer2; i++) {
            User user = new User();
            game2.getPlayers().add(user);
        }
        game2.initShipsCards();
        assertNotNull(game2.getShipsCards());
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
        assertEquals(game.getPlayers().indexOf(nextPlayer), game.getPlayers().indexOf(firstPlayer) + 1);
    }

}