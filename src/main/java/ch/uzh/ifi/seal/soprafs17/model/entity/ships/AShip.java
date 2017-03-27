package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.exception.PlaceUnavailableException;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.exception.ShipException;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.exception.ShipIsFullException;
import com.sun.javafx.beans.IDProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ilecipi on 27.03.17.
 */
@Entity
@Inheritance
@DiscriminatorColumn(name="ship_type")
public abstract class AShip implements IShip, Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column
    protected Stone[] stones;

    @Column
    private int addedStones = 0;

    AShip() {
        this.initShips();
    }

    @Override
    public void addStone(Stone stone, int i)
        throws ShipException {
        if (this.addedStones >= this.getMaxStones()) {
            throw new ShipIsFullException();
        }

        if (this.stones[i] != null) {
            throw new PlaceUnavailableException();
        }

        this.stones[i] = stone;
        this.addedStones++;
    }

    @Override
    public Stone removeStone(int i) {
        if(addedStones == this.getMinStones() && i == 0){
            Stone removedStone = stones[i];
            stones[i] = null;
            addedStones--;
            return removedStone;
        }else{
            return null;
        }
    }

    @Override
    public boolean isReady() {
        return false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public abstract int getMaxStones();
    public abstract int getMinStones();

    protected abstract void initShips();
}
