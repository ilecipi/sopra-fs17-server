package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.exception.ShipException;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ilecipi on 10.03.17.
 */

@Entity
@DiscriminatorValue("four_seated")
public class FourSeatedShip extends AShip {
    private static final int MIN_STONES_REQUIRED = 3;
    private static final int MAX_STONES_REQUIRED = 4;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    private Long id;

    public FourSeatedShip() {
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
        this.stones = new Stone[MAX_STONES_REQUIRED];
    }
}
