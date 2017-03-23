package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ilecipi on 10.03.17.
 */

@Entity
public class TwoSeatedShip  extends ShipFactory implements IShip, Serializable {
    final int MIN_STONES_REQUIRED=1;
    final int MAX_STONES_POSSIBLE=2;


    @Id
    @GeneratedValue
    private long id;

    @Override
    public void addStone(Stone stone, int i) {
        if(addedStones < MAX_STONES_POSSIBLE && stones[i] == null && i >= 0 && i < MAX_STONES_POSSIBLE){
            stones[i] = stone;
            addedStones++;
        }
    }

    @Override
    public Stone removeStone(int i) {
        if(addedStones >= 0 && i >= 0 && i < MAX_STONES_POSSIBLE && stones[i] != null){
            Stone removedStone= stones[i];
            stones[i] = null;
            addedStones--;
            return removedStone;
        }else{
            return null;
        }
    }

    @Override
    public boolean isReady() {
        if(addedStones >= MIN_STONES_REQUIRED && addedStones <= MAX_STONES_POSSIBLE){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void setStones(Stone[] stones) {
        super.setStones(new Stone[this.MAX_STONES_POSSIBLE]);
    }
}
