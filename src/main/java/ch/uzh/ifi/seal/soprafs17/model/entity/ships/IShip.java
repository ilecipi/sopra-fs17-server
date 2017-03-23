package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;

import java.io.Serializable;

/**
 * Created by erion on 17.03.17.
 */
public interface IShip {
    public void addStone(Stone stone, int i);
    public Stone removeStone(int i);
    public boolean isReady();

}
