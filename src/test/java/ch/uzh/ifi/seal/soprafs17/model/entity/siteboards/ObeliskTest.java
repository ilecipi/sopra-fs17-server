package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by ilecipi on 12.04.17.
 * his class tests that the stone in the pyramid are properly added and the points
 * are properly assigned to users
 */
public class ObeliskTest {

    @Test
    public void addStone() throws Exception {
        Obelisk obeliskFourPlayers = new Obelisk(4);
        assertEquals(4,  obeliskFourPlayers.getNumberOfPlayers());
        assertEquals(new Integer(0),  obeliskFourPlayers.getObelisks().get("white"));
         obeliskFourPlayers.addStone(new Stone("white"));
        assertEquals(new Integer(1),  obeliskFourPlayers.getObelisks().get("white"));
         obeliskFourPlayers.addStone(new Stone("black"));
        assertEquals(new Integer(1),  obeliskFourPlayers.getObelisks().get("black"));
         obeliskFourPlayers.addStone(new Stone("brown"));
        assertEquals(new Integer(1),  obeliskFourPlayers.getObelisks().get("brown"));
         obeliskFourPlayers.addStone(new Stone("grey"));
        assertEquals(new Integer(1),  obeliskFourPlayers.getObelisks().get("grey"));

        Obelisk obeliskThreePlayers = new Obelisk(3);
        assertEquals(3,  obeliskThreePlayers.getNumberOfPlayers());
        assertEquals(new Integer(0),  obeliskThreePlayers.getObelisks().get("white"));
        obeliskThreePlayers.addStone(new Stone("white"));
        assertEquals(new Integer(1),  obeliskThreePlayers.getObelisks().get("white"));
        obeliskThreePlayers.addStone(new Stone("black"));
        assertEquals(new Integer(1),  obeliskThreePlayers.getObelisks().get("black"));
        obeliskThreePlayers.addStone(new Stone("brown"));
        assertEquals(new Integer(1),  obeliskThreePlayers.getObelisks().get("brown"));

        Obelisk obeliskTwoPlayers = new Obelisk(2);
        assertEquals(2,  obeliskTwoPlayers.getNumberOfPlayers());
        assertEquals(new Integer(0),  obeliskTwoPlayers.getObelisks().get("white"));
        obeliskTwoPlayers.addStone(new Stone("white"));
        assertEquals(new Integer(1),  obeliskTwoPlayers.getObelisks().get("white"));
        obeliskTwoPlayers.addStone(new Stone("black"));
        assertEquals(new Integer(1),  obeliskTwoPlayers.getObelisks().get("black"));

    }

    @Test
    public void countEndOfGame() throws Exception {
        Obelisk obeliskFourPlayers = new Obelisk(4);
        obeliskFourPlayers.addStone(new Stone("white"));
        obeliskFourPlayers.addStone(new Stone("black"));
        obeliskFourPlayers.addStone(new Stone("brown"));
        obeliskFourPlayers.addStone(new Stone("grey"));

        Obelisk obeliskThreePlayers = new Obelisk(3);
        obeliskThreePlayers.addStone(new Stone("white"));
        obeliskThreePlayers.addStone(new Stone("black"));
        obeliskThreePlayers.addStone(new Stone("brown"));

        Obelisk obeliskTwoPlayers = new Obelisk(2);

        obeliskTwoPlayers.addStone(new Stone("black"));
        obeliskTwoPlayers.addStone(new Stone("white"));

        //Test for 2 players
        Map<String, Integer> pointsTwoPlayers = obeliskTwoPlayers.countEndOfGame();
        assertEquals(new Integer(5), pointsTwoPlayers.get("white"));
        assertEquals(new Integer(5), pointsTwoPlayers.get("black"));

        obeliskTwoPlayers.addStone(new Stone("white"));
        pointsTwoPlayers = obeliskTwoPlayers.countEndOfGame();
        assertEquals(new Integer(10), pointsTwoPlayers.get("white"));
        assertEquals(new Integer(1), pointsTwoPlayers.get("black"));

        //Test for 3 players

        //same amount of stones
        obeliskThreePlayers.addStone(new Stone("black"));
        obeliskThreePlayers.addStone(new Stone("white"));
        obeliskThreePlayers.addStone(new Stone("brown"));

        Map<String, Integer> pointsThreePlayers = obeliskThreePlayers.countEndOfGame();

        assertEquals(new Integer(6), pointsThreePlayers.get("black"));
        assertEquals(new Integer(6), pointsThreePlayers.get("white"));
        assertEquals(new Integer(6), pointsThreePlayers.get("brown"));

        //black wins, white&brown same amount
        obeliskThreePlayers.addStone(new Stone("black"));

        pointsThreePlayers = obeliskThreePlayers.countEndOfGame();

        assertEquals(new Integer(12), pointsThreePlayers.get("black"));
        assertEquals(new Integer(3), pointsThreePlayers.get("white"));
        assertEquals(new Integer(3), pointsThreePlayers.get("brown"));

        //black wins, white is second, brown is last one
        obeliskThreePlayers.addStone(new Stone("black"));
        obeliskThreePlayers.addStone(new Stone("white"));

        pointsThreePlayers = obeliskThreePlayers.countEndOfGame();

        assertEquals(new Integer(12), pointsThreePlayers.get("black"));
        assertEquals(new Integer(6), pointsThreePlayers.get("white"));
        assertEquals(new Integer(1), pointsThreePlayers.get("brown"));

        //black&white wins, brown is last
        obeliskThreePlayers.addStone(new Stone("white"));
        pointsThreePlayers = obeliskThreePlayers.countEndOfGame();

        assertEquals(new Integer(9), pointsThreePlayers.get("black"));
        assertEquals(new Integer(9), pointsThreePlayers.get("white"));
        assertEquals(new Integer(1), pointsThreePlayers.get("brown"));

        //black&white same amount, brown is not on the obelisk
        obeliskThreePlayers = new Obelisk(3);
        obeliskThreePlayers.addStone(new Stone("white"));
        obeliskThreePlayers.addStone(new Stone("black"));
        pointsThreePlayers.remove("brown");
        pointsThreePlayers = obeliskThreePlayers.countEndOfGame();
        assertEquals(new Integer(9), pointsThreePlayers.get("black"));
        assertEquals(new Integer(9), pointsThreePlayers.get("white"));
        assertNull(pointsThreePlayers.get("brown"));

        //Test for four players
        Map<String, Integer> pointsFourPlayers = obeliskFourPlayers.countEndOfGame();

        //Same amount of stones
        assertEquals(new Integer(7), pointsFourPlayers.get("black"));
        assertEquals(new Integer(7), pointsFourPlayers.get("white"));
        assertEquals(new Integer(7), pointsFourPlayers.get("brown"));
        assertEquals(new Integer(7), pointsFourPlayers.get("grey"));

        //Black wins, others same amount
        obeliskFourPlayers.addStone(new Stone("black"));

        pointsFourPlayers = obeliskFourPlayers.countEndOfGame();
        assertEquals(new Integer(15), pointsFourPlayers.get("black"));
        assertEquals(new Integer(5), pointsFourPlayers.get("white"));
        assertEquals(new Integer(5), pointsFourPlayers.get("brown"));
        assertEquals(new Integer(5), pointsFourPlayers.get("grey"));

        //black&white same amoount, other two as well
        obeliskFourPlayers.addStone(new Stone("white"));
        pointsFourPlayers = obeliskFourPlayers.countEndOfGame();
        assertEquals(new Integer(12), pointsFourPlayers.get("black"));
        assertEquals(new Integer(12), pointsFourPlayers.get("white"));
        assertEquals(new Integer(3), pointsFourPlayers.get("brown"));
        assertEquals(new Integer(3), pointsFourPlayers.get("grey"));

        //black,white,brown same amount, grey loses
        obeliskFourPlayers.addStone(new Stone("brown"));
        pointsFourPlayers = obeliskFourPlayers.countEndOfGame();
        assertEquals(new Integer(10), pointsFourPlayers.get("black"));
        assertEquals(new Integer(10), pointsFourPlayers.get("white"));
        assertEquals(new Integer(10), pointsFourPlayers.get("brown"));
        assertEquals(new Integer(1), pointsFourPlayers.get("grey"));

        //black wins, white second, brown&grey tie
        obeliskFourPlayers.addStone(new Stone("black"));
        obeliskFourPlayers.addStone(new Stone("black"));
        obeliskFourPlayers.addStone(new Stone("white"));
        obeliskFourPlayers.addStone(new Stone("grey"));
        pointsFourPlayers = obeliskFourPlayers.countEndOfGame();
        assertEquals(new Integer(15), pointsFourPlayers.get("black"));
        assertEquals(new Integer(10), pointsFourPlayers.get("white"));
        assertEquals(new Integer(3), pointsFourPlayers.get("brown"));
        assertEquals(new Integer(3), pointsFourPlayers.get("grey"));

        //black first, white second, brown third, grey last
        obeliskFourPlayers.getObelisks().replace("grey",1);
        pointsFourPlayers = obeliskFourPlayers.countEndOfGame();
        assertEquals(new Integer(15), pointsFourPlayers.get("black"));
        assertEquals(new Integer(10), pointsFourPlayers.get("white"));
        assertEquals(new Integer(5), pointsFourPlayers.get("brown"));
        assertEquals(new Integer(1), pointsFourPlayers.get("grey"));

        //black wins, white&brown tie, grey loses
        obeliskFourPlayers.addStone(new Stone("brown"));
        pointsFourPlayers = obeliskFourPlayers.countEndOfGame();
        assertEquals(new Integer(15), pointsFourPlayers.get("black"));
        assertEquals(new Integer(7), pointsFourPlayers.get("white"));
        assertEquals(new Integer(7), pointsFourPlayers.get("brown"));
        assertEquals(new Integer(1), pointsFourPlayers.get("grey"));

        //black&white wins and same, the others different
        obeliskFourPlayers.addStone(new Stone("white"));
        pointsFourPlayers = obeliskFourPlayers.countEndOfGame();
        assertEquals(new Integer(12), pointsFourPlayers.get("black"));
        assertEquals(new Integer(12), pointsFourPlayers.get("white"));
        assertEquals(new Integer(5), pointsFourPlayers.get("brown"));
        assertEquals(new Integer(1), pointsFourPlayers.get("grey"));

        //Three players on the obelisk, the fourth is not on it
        pointsFourPlayers.remove("grey");
        obeliskFourPlayers = new Obelisk(4);
        obeliskFourPlayers.addStone(new Stone("white"));
        obeliskFourPlayers.addStone(new Stone("black"));
        obeliskFourPlayers.addStone(new Stone("brown"));
        pointsFourPlayers = obeliskFourPlayers.countEndOfGame();

        //everybody has the same amount of stones on it
        assertEquals(new Integer(10), pointsFourPlayers.get("black"));
        assertEquals(new Integer(10), pointsFourPlayers.get("white"));
        assertEquals(new Integer(10), pointsFourPlayers.get("brown"));

        //first&second the same, third one different
        obeliskFourPlayers.addStone(new Stone("white"));
        obeliskFourPlayers.addStone(new Stone("black"));
        pointsFourPlayers = obeliskFourPlayers.countEndOfGame();
        assertEquals(new Integer(12), pointsFourPlayers.get("black"));
        assertEquals(new Integer(12), pointsFourPlayers.get("white"));
        assertEquals(new Integer(5), pointsFourPlayers.get("brown"));

        //first different, second and third the same
        obeliskFourPlayers.addStone(new Stone("black"));
        obeliskFourPlayers.addStone(new Stone("brown"));
        pointsFourPlayers = obeliskFourPlayers.countEndOfGame();
        assertEquals(new Integer(15), pointsFourPlayers.get("black"));
        assertEquals(new Integer(7), pointsFourPlayers.get("white"));
        assertEquals(new Integer(7), pointsFourPlayers.get("brown"));

        //Two players on the obelisk
        pointsFourPlayers.remove("brown");
        obeliskFourPlayers = new Obelisk(4);
        obeliskFourPlayers.addStone(new Stone("white"));
        obeliskFourPlayers.addStone(new Stone("black"));
        pointsFourPlayers = obeliskFourPlayers.countEndOfGame();
        assertEquals(new Integer(12), pointsFourPlayers.get("white"));
        assertEquals(new Integer(12), pointsFourPlayers.get("black"));
    }

}