package ch.uzh.ifi.seal.soprafs17.model.entity.moves;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;


/**
 * Created by tonio99tv on 24/04/17.
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GetStoneMoveTest {


    @Autowired
    GameService gameService;
    private Game game;
    private User user;
    private GetStoneMove move;

    //    @BeforeClass
//    public static void onlyOnce() {
//
//    }
//
    @Before
    public void beforeEach() {
        this.game = new Game();
        this.user = new User();
        this.game.getPlayers().add(this.user);
        this.move = new GetStoneMove();
        this.move.setUser(this.user);
        this.move.setGame(this.game);
    }

    @Test
    public void getId() throws Exception {

    }

    @Test
    public void setId() throws Exception {
        GetStoneMove move = new GetStoneMove();
        move.setId(new Long(1));
        assertNotNull(move.getId());
    }

    @Test
    public void getStones() throws Exception {
        //supply shed without stones
        this.user.setStoneQuarry(3);
        this.user.setSupplySled(0);
        this.move.getStones(this.game);
        assertEquals(3, this.user.getSupplySled());
        assertEquals(0, this.user.getStoneQuarry());

        this.user.setStoneQuarry(2);
        this.user.setSupplySled(0);
        this.move.getStones(this.game);
        assertEquals(2, this.user.getSupplySled());
        assertEquals(0, this.user.getStoneQuarry());

        this.user.setStoneQuarry(1);
        this.user.setSupplySled(0);
        this.move.getStones(this.game);
        assertEquals(1, this.user.getSupplySled());
        assertEquals(0, this.user.getStoneQuarry());

        //supply shed with 1 stone
        this.user.setStoneQuarry(3);
        this.user.setSupplySled(1);
        this.move.getStones(this.game);
        assertEquals(4, this.user.getSupplySled());
        assertEquals(0, this.user.getStoneQuarry());

        this.user.setStoneQuarry(2);
        this.user.setSupplySled(1);
        this.move.getStones(this.game);
        assertEquals(3, this.user.getSupplySled());
        assertEquals(0, this.user.getStoneQuarry());

        this.user.setStoneQuarry(1);
        this.user.setSupplySled(1);
        this.move.getStones(this.game);
        assertEquals(2, this.user.getSupplySled());
        assertEquals(0, this.user.getStoneQuarry());

        //supply shed with 2 stones
        this.user.setStoneQuarry(3);
        this.user.setSupplySled(2);
        this.move.getStones(this.game);
        assertEquals(5, this.user.getSupplySled());
        assertEquals(0, this.user.getStoneQuarry());

        this.user.setStoneQuarry(2);
        this.user.setSupplySled(2);
        this.move.getStones(this.game);
        assertEquals(4, this.user.getSupplySled());
        assertEquals(0, this.user.getStoneQuarry());

        this.user.setStoneQuarry(1);
        this.user.setSupplySled(2);
        this.move.getStones(this.game);
        assertEquals(3, this.user.getSupplySled());
        assertEquals(0, this.user.getStoneQuarry());

        //supply shed with 3 stones
        this.user.setStoneQuarry(3);
        this.user.setSupplySled(3);
        this.move.getStones(this.game);
        assertEquals(5, this.user.getSupplySled());
        assertEquals(1, this.user.getStoneQuarry());

        this.user.setStoneQuarry(2);
        this.user.setSupplySled(3);
        this.move.getStones(this.game);
        assertEquals(5, this.user.getSupplySled());
        assertEquals(0, this.user.getStoneQuarry());

        this.user.setStoneQuarry(1);
        this.user.setSupplySled(3);
        this.move.getStones(this.game);
        assertEquals(4, this.user.getSupplySled());
        assertEquals(0, this.user.getStoneQuarry());

        //supply shed with 4 stones
        this.user.setStoneQuarry(1);
        this.user.setSupplySled(4);
        this.move.getStones(this.game);
        assertEquals(5, this.user.getSupplySled());
        assertEquals(0, this.user.getStoneQuarry());


    }

}
