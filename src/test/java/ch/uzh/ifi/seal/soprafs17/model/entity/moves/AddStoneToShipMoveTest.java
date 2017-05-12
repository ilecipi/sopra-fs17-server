package ch.uzh.ifi.seal.soprafs17.model.entity.moves;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.OneSeatedShip;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by tonio99tv on 03/05/17.
 * This class tests some property of the add stone to ship move
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AddStoneToShipMoveTest {

    private Game game;
    private User user;
    private AShip ship;
    private int position;
    private Round round;
    private AddStoneToShipMove move;

    @Before
    public  void beforeEach(){
        this.game = new Game();
        this.user = new User();
        this.user.setColor("black");
        this.user.setSupplySled(1);
        this.game.getPlayers().add(this.user);
        this.ship = new OneSeatedShip();
        this.round = new Round();
        this.position = 0;

        this.move = new AddStoneToShipMove();
        this.move.setGame(this.game);
        this.move.setUser(this.user);
        this.move.setShip(this.ship);
        this.move.setRound(this.round);
        this.move.setPosition(this.position);
        this.move.getRound().setActionCardHammer(true);
        this.move.getRound().setIsActionCardSail(2);
        this.move.getRound().setIsActionCardChisel(1);

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
    public void setPosition() throws Exception{
        assertNotNull(this.move.getPosition());
    }

    @Test
    public void stoneIsCorrrectColor() throws Exception{
        assertEquals(this.user.getColor(),this.move.getShip().getStones()[this.move.getPosition()].getColor());
    }

    @Test
    public void supplySledDecreased() throws Exception{
        assertEquals(0,this.move.getUser().getSupplySled());
    }

    @Test
    public void stoneWasAdded() throws Exception{
        assertNotNull(this.move.getShip().getStones()[this.move.getPosition()]);
    }

    @Test
    public void actionCardHammerCorrect() throws Exception{
        assertEquals(false,this.move.getRound().isActionCardHammer());
    }

    @Test
    public void actionCardSailCorrect() throws Exception{
        assertEquals(1,this.move.getRound().getIsActionCardSail());
    }

    @Test
    public void actionCardChiselCorrect() throws Exception{
        assertEquals(0,this.move.getRound().getIsActionCardChisel());
    }
}
