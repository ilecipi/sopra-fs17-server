package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by erion on 12.04.17.
 */
public class PyramidTest {

    @Test
    public void addStone() throws Exception {
        Pyramid pyramid = new Pyramid();
        Stone stone1 = new Stone();
        stone1.setColor("black");
        pyramid.addStone(stone1);
        assertEquals(pyramid.getCounter(),1);
        assertNotNull(pyramid.getAddedStones());
    }

    @Test
    public void countAfterMove() throws Exception {
        Pyramid pyramid = new Pyramid();
        pyramid.addStone(new Stone("black"));
        pyramid.addStone(new Stone("brown"));
        pyramid.addStone(new Stone("black"));
        pyramid.addStone(new Stone("white"));
        pyramid.addStone(new Stone("grey"));

        Map<String,Integer> points = pyramid.countAfterMove();
        assertNotNull(points);
        assertEquals(new Integer(5),points.get("black"));
        assertEquals(new Integer(2),points.get("white"));
        assertEquals(new Integer(1),points.get("brown"));
        assertEquals(new Integer(4),points.get("grey"));

        pyramid.addStone(new Stone("black"));
        pyramid.addStone(new Stone("brown"));
        pyramid.addStone(new Stone("black"));
        pyramid.addStone(new Stone("white"));
        pyramid.addStone(new Stone("grey"));
        pyramid.addStone(new Stone("black"));
        pyramid.addStone(new Stone("brown"));
        pyramid.addStone(new Stone("black"));
        pyramid.addStone(new Stone("white"));
        pyramid.addStone(new Stone("grey"));
        points = pyramid.countAfterMove();

        assertEquals(new Integer(15),points.get("black"));
        assertEquals(new Integer(9),points.get("white"));
        assertEquals(new Integer(4),points.get("brown"));
        assertEquals(new Integer(7),points.get("grey"));
    }

    @Test
    public void countEndOfRound() throws Exception {
        Pyramid pyramid = new Pyramid();
        assertNull(pyramid.countEndOfRound());
    }

    @Test
    public void countEndOfGame() throws Exception {
        Pyramid pyramid = new Pyramid();
        assertNull(pyramid.countEndOfGame());
    }

}