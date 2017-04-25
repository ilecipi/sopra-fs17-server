package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.exception.ShipException;

/**
 * Created by erion on 17.03.17.
 */
public interface IShip {
    void addStone(Stone stone, int i)
            throws ShipException;

    Stone removeStone(int i);

    boolean isReady();
}
