package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.exception.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long id;

    @Column
    public Stone[] stones;


    @Column
    private boolean isReady;

    @Column
    @JsonIgnore
    private int addedStones = 0;

    @Column
    private boolean docked;

    public boolean isDocked() {
        return docked;
    }

    public void setDocked(boolean docked) {
        this.docked = docked;
    }


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
        if(this.addedStones==this.getMinStones()){
            return true;
        }else{
            return false;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract int getMaxStones();
    public abstract int getMinStones();

    protected abstract void initShips();

    public Stone[] getStones() {
        return stones;
    }
    public int getAddedStones() {
        return addedStones;
    }
    public void setReady(boolean ready) {
        this.isReady = ready;
    }

}
