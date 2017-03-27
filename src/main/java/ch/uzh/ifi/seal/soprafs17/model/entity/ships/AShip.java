package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.exception.*;
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
    public void addStone(Stone stone, int i) throws ShipException {
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
        if(this.addedStones==0){
            throw new ShipIsEmptyException();
        }
        if(stones[i]==null){
            throw new RemoveUnavailableException();
        }
            Stone tmp = stones[i];
            stones[i] = null;
            addedStones--;
            return tmp;
    }

    @Override
    public boolean isReady() {
        if(addedStones==this.getMinStones()){
            return true;
        }else{
            return false;
        }
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
