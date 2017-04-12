package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by ilecipi on 12.04.17.
 */
public class BurialChamberTest {

    @Test
    public void addStone() throws Exception {
        BurialChamber burialChamber = new BurialChamber();
        Stone stone1 = new Stone();
        stone1.setColor("black");
        Stone stone2 = new Stone();
        stone2.setColor("white");
        Stone stone3 = new Stone();
        stone3.setColor("brown");
        burialChamber.addStone(stone1);
        assertNotNull(burialChamber.getFirstRow());
        assertEquals(stone1, burialChamber.getFirstRow().get(0));
        assertEquals(0, burialChamber.getSecondRow().size());
        assertEquals(0, burialChamber.getThirdRow().size());
        burialChamber.addStone(stone2);
        assertNotNull(burialChamber.getSecondRow());
        assertEquals(stone1, burialChamber.getFirstRow().get(0));
        assertEquals(stone2, burialChamber.getSecondRow().get(0));
        assertEquals(0, burialChamber.getThirdRow().size());
        burialChamber.addStone(stone3);
        assertNotNull(burialChamber.getFirstRow());
        assertEquals(stone1, burialChamber.getFirstRow().get(0));
        assertEquals(stone2, burialChamber.getSecondRow().get(0));
        assertEquals(stone3, burialChamber.getThirdRow().get(0));
    }

    @Test
    public void countEndOfGame() throws Exception {
        BurialChamber burialChamber = new BurialChamber();
        burialChamber.setFirstRow(new ArrayList<>());
        burialChamber.setSecondRow(new ArrayList<>());
        burialChamber.setThirdRow(new ArrayList<>());

        //        TESTING PURPOSES
        //1
        burialChamber.getFirstRow().add(new Stone("brown"));
        burialChamber.getSecondRow().add(new Stone("grey"));
        burialChamber.getThirdRow().add(new Stone("white"));
        //2
        burialChamber.getFirstRow().add(new Stone("grey"));
        burialChamber.getSecondRow().add(new Stone("brown"));
        burialChamber.getThirdRow().add(new Stone("white"));
        //3
        burialChamber.getFirstRow().add(new Stone("black"));
        burialChamber.getSecondRow().add(new Stone("black"));
        burialChamber.getThirdRow().add(new Stone("black"));
        //4
        burialChamber.getFirstRow().add(new Stone("black"));
        burialChamber.getSecondRow().add(new Stone("black"));
        burialChamber.getThirdRow().add(new Stone("black"));
        //5
        burialChamber.getFirstRow().add(new Stone("black"));
        burialChamber.getSecondRow().add(new Stone("black"));
        burialChamber.getThirdRow().add(new Stone("black"));
        //6
        burialChamber.getFirstRow().add(new Stone("white"));
        burialChamber.getSecondRow().add(new Stone("white"));
        burialChamber.getThirdRow().add(new Stone("white"));
        //7
        burialChamber.getFirstRow().add(new Stone("white"));
        burialChamber.getSecondRow().add(new Stone("white"));
        burialChamber.getThirdRow().add(new Stone("white"));
        //8
        burialChamber.getFirstRow().add(new Stone("brown"));
        burialChamber.getSecondRow().add(new Stone("white"));
        burialChamber.getThirdRow().add(new Stone("brown"));

        assertEquals(8, burialChamber.getFirstRow().size());
        assertEquals(8, burialChamber.getSecondRow().size());
        assertEquals(8, burialChamber.getThirdRow().size());
        Map<String,Integer> points = burialChamber.countEndOfGame();
        assertNotNull(points);
        assertEquals(new Integer(21), points.get("white"));
        assertEquals(new Integer(23), points.get("black"));
        assertEquals(new Integer(4), points.get("brown"));
        assertEquals(new Integer(2), points.get("grey"));
    }

}