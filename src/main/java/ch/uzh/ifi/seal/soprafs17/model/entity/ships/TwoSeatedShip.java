package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.exception.ShipException;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ilecipi on 10.03.17.
 */

@Entity
public class TwoSeatedShip  extends AShip {
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
