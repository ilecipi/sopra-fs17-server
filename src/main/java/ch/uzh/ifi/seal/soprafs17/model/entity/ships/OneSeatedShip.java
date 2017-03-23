package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;

import java.io.Serializable;


public class OneSeatedShip extends ShipFactory implements IShip, Serializable {
    final int MIN_STONES_REQUIRED=1;
    final int MAX_STONES_POSSIBLE=1;

    Stone[] stones = new Stone[MAX_STONES_POSSIBLE];
    int addedStones = 0;


    @Override
    public void addStone(Stone stone, int i) {
        if(addedStones == 0 && i == 0){
            stones[i] = stone;
            addedStones++;
        }
    }

    @Override
    public Stone removeStone(int i) {
        if(addedStones == MIN_STONES_REQUIRED && i == 0){
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
        if(addedStones == MIN_STONES_REQUIRED){
            return true;
        }else{
            return false;
        }
    }
}