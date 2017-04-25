package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.exception.*;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ilecipi on 27.03.17.
 */
@Entity
@Inheritance
@DiscriminatorColumn(name = "ship_type")
public abstract class AShip implements IShip, Serializable {

    @Column
    Stone[] stones;
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private boolean isReady;

    @Column
    @JsonIgnore
    private int addedStones = 0;

    @Column
    private boolean docked;
    @OneToOne
    private SiteBoard siteBoard;

    AShip() {
        this.initShips();
    }

    public SiteBoard getSiteBoard() {
        return siteBoard;
    }

    public void setSiteBoard(SiteBoard siteBoard) {
        this.siteBoard = siteBoard;
    }

    public boolean isDocked() {
        return docked;
    }

    public void setDocked(boolean docked) {
        this.docked = docked;
    }

    @Override
    public void addStone(Stone stone, int position) throws ShipException {
        if (this.addedStones >= this.getMaxStones()) {
            throw new ShipIsFullException();
        }

        if (this.stones[position] != null) {
            throw new PlaceUnavailableException();
        }

        this.stones[position] = stone;
        this.addedStones++;
    }

    @Override
    public Stone removeStone(int position) {
        if (this.addedStones == 0) {
            throw new ShipIsEmptyException();
        }
        if (stones[position] == null) {
            throw new RemoveUnavailableException();
        }
        Stone tmp = stones[position];
        stones[position] = null;
        addedStones--;
        return tmp;
    }

    @Override
    public boolean isReady() {
        if (this.addedStones == this.getMinStones() || this.addedStones == this.getMaxStones()) {
            return true;
        } else {
            return false;
        }
    }

    public void setReady(boolean ready) {
        this.isReady = ready;
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

}
