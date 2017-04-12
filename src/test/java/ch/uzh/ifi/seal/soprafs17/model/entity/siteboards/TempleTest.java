package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by erion on 12.04.17.
 */
public class TempleTest {

    @Test
    public void setStones() throws Exception {
        Temple temple = new Temple();
        int numberOfUsers = 2;
        Stone[] stones = null;
        temple.setStones(stones,numberOfUsers);
        assertEquals(4,temple.getStones().length);

        numberOfUsers=3;
        temple.setStones(stones,numberOfUsers);
        assertEquals(5,temple.getStones().length);

        numberOfUsers=4;
        temple.setStones(stones,numberOfUsers);
        assertEquals(5,temple.getStones().length);
    }

    @Test
    public void countAfterMove() throws Exception {
        Temple temple = new Temple();
        assertNull(temple.countAfterMove());
    }

    @Test
    public void countEndOfRound() throws Exception {
        Temple temple = new Temple();
        int numberOfUsers = 2;
        Stone[] stones = null;
        temple.setStones(stones,numberOfUsers);
        temple.addStone(new Stone("black"));
        temple.addStone(new Stone("white"));
        temple.addStone(new Stone("black"));
        temple.addStone(new Stone("white"));

        Map<String,Integer> points = temple.countEndOfRound();
        assertNotNull(points);
        assertEquals(new Integer(2),points.get("black"));
        assertEquals(new Integer(2),points.get("white"));

        temple.addStone(new Stone("white"));
        points = temple.countEndOfRound();
        assertEquals(new Integer(1),points.get("black"));
        assertEquals(new Integer(3),points.get("white"));

    }

    @Test
    public void countEndOfGame() throws Exception {
        Temple temple = new Temple();
        assertNull(temple.countEndOfGame());
    }

    @Test
    public void addStone() throws Exception {
        Temple temple = new Temple();
        Stone[] stones = null;
        int numberOfPlayers = 4;
        temple.setStones(stones,numberOfPlayers);
        temple.addStone(new Stone("black"));
        assertNotNull(temple.getStones()[0]);
        temple.addStone(new Stone("black"));
        temple.addStone(new Stone("black"));
        temple.addStone(new Stone("black"));
        temple.addStone(new Stone("black"));
        temple.addStone(new Stone("white"));
        temple.addStone(new Stone("white"));
        assertEquals("white",temple.getStones()[0].getColor());
        assertEquals("white",temple.getStones()[1].getColor());

    }

}