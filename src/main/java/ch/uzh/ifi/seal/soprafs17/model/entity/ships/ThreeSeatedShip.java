package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;

import java.io.Serializable;

/**
 * Created by ilecipi on 10.03.17.
 */

public class ThreeSeatedShip extends ShipFactory  implements IShip, Serializable {

    final int MIN_STONES_REQUIRED=2;
    final int MAX_STONES_POSSIBLE=3;

    Stone[] stones = new Stone[3];
    int addedStones = 0;

    @Override
    public void addStone(Stone stone, int i) {
        if(addedStones < MAX_STONES_POSSIBLE && i >= 0 && i < MAX_STONES_POSSIBLE && stones[i] == null){
            stones[i] = stone;
            addedStones++;
        }
    }

    @Override
    public Stone removeStone(int i) {
        if(i >= 0 && i < MAX_STONES_POSSIBLE && addedStones>0 && stones[i] != null){
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
        if(addedStones >= MIN_STONES_REQUIRED){
            return true;
        }else {
            return false;
        }
    }
}
