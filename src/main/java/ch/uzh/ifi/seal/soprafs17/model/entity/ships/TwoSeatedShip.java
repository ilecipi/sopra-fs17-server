package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.exception.ShipException;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ilecipi on 10.03.17.
 */

@Entity
@DiscriminatorValue("two_seated")
public class TwoSeatedShip  extends AShip {
    private static final int MIN_STONES_REQUIRED = 1;
    private static final int MAX_STONES_REQUIRED = 2;

    @Id
    @GeneratedValue
    private long id;

    public TwoSeatedShip(){
        super();
    }

    @Override
    public int getMaxStones() {
        return this.MAX_STONES_REQUIRED;
    }

    @Override
    public int getMinStones() {
        return this.MIN_STONES_REQUIRED;
    }

    @Override
    protected void initShips() {
        this.stones= new Stone[MAX_STONES_REQUIRED];
    }
}
