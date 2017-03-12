package ch.uzh.ifi.seal.soprafs17.Ships;

import ch.uzh.ifi.seal.soprafs17.entity.Stone;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ile Cepilov on 10.03.17.
 */

public abstract class Ship {

    private List<Stone> stones;
    private boolean isReady;
    private int minNrOfSeats;
    private int numberOfSeats;

    public Ship(int minNrOfSeats, int numberOfSeats) {
        this.stones = new ArrayList<>();
        this.minNrOfSeats = minNrOfSeats;
        this.numberOfSeats = numberOfSeats;
    }
}
