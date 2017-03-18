package ch.uzh.ifi.seal.soprafs17.model.entity.Ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;

/**
 * Created by ilecipi on 10.03.17.
 */
public class TwoSeatedShip implements IShip{
    final int MIN_STONES_REQUIRED=1;
    final int MAX_STONES_POSSIBLE=2;

    Stone[] stones = new Stone[MAX_STONES_POSSIBLE];

    int addedStones = 0;


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
}
