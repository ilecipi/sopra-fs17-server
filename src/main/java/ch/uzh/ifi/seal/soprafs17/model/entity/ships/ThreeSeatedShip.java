package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by ilecipi on 10.03.17.
 */

@Entity
@DiscriminatorValue("three_seated")
public class ThreeSeatedShip extends AShip {
    private static final int MIN_STONES_REQUIRED = 2;
    private static final int MAX_STONES_REQUIRED = 3;
    @Id
    @GeneratedValue
    private Long id;

    public ThreeSeatedShip() {
        super();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
