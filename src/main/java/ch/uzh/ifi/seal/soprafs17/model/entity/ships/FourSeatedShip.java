package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.exception.ShipException;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ilecipi on 10.03.17.
 */

public class FourSeatedShip extends AShip {

    final int MIN_STONES_REQUIRED=3;
    final int MAX_STONES_POSSIBLE=4;


    @Override
    public int getMaxStones() {
        return 0;
    }

    @Override
    public int getMinStones() {
        return 0;
    }

    @Override
    protected void initShips() {

    }
}
