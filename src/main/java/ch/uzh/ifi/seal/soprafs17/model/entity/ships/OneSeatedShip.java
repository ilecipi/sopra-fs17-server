package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue("one_seated")
public class OneSeatedShip extends AShip {
    private static final int MIN_STONES_REQUIRED=1;
    private static final int MAX_STONES_POSSIBLE=1;


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

    public OneSeatedShip() {
        super();
    }


    public int getMaxStones() {
        return this.MAX_STONES_POSSIBLE;
    }
    public int getMinStones() {
        return this.MIN_STONES_REQUIRED;
    }

    protected void initShips() {
        this.stones = new Stone[MAX_STONES_POSSIBLE];
    }
}
