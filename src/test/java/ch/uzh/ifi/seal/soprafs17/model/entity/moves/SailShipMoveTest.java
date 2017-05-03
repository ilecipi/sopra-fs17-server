package ch.uzh.ifi.seal.soprafs17.model.entity.moves;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.OneSeatedShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by tonio99tv on 03/05/17.
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SailShipMoveTest {


    private Game game;
    private User user;
    private AShip ship;
    private Round round;
    private SiteBoard siteBoard;
    private SailShipMove move;





    //    @BeforeClass
//    public static void onlyOnce() {
//
//    }
//
    @Before
    public void beforeEach(){
        //create necessary objects for the move
        this.game = new Game();
        this.user = new User();
        this.game.getPlayers().add(this.user);
        this.ship = new OneSeatedShip();
        this.round = new Round();
        this.siteBoard = new Temple();

        //create the move and set elements
        this.move = new SailShipMove();
        this.move.setGame(this.game);
        this.move.setUser(this.user);
        this.move.setShip(this.ship);
        this.move.setRound(this.round);
        this.move.setSiteBoard(this.siteBoard);

        //make the move
        this.move.makeMove(this.game);
    }

    @Test
    public void setId() throws Exception {
        this.move.setId(new Long(1));
        assertNotNull(this.move.getId());
    }

    @Test
    public void setShip() throws Exception{
        assertNotNull(this.move.getShip());
    }

    @Test
    public void setSiteBoard() throws Exception {
        assertNotNull(this.move.getSiteBoard());
    }

    @Test
    public void shipIsDocked() throws Exception{
        assertEquals(true,this.move.getShip().isDocked());

    }

    @Test
    public void correspondingSiteBoard() throws Exception{
        assertEquals(this.siteBoard,this.move.getShip().getSiteBoard());

    }

    @Test
    public void siteBoardIsOccupied() throws Exception{
        assertEquals(true, this.move.getSiteBoard().isOccupied());
    }

    @Test
    public void correspondingShip() throws Exception{
        assertEquals(this.ship,this.move.getSiteBoard().getDockedShip());
    }


}

