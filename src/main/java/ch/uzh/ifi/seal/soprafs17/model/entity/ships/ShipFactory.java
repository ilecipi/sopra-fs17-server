package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erion on 17.03.17.
 */
@Component
public class ShipFactory {

    public static OneSeatedShip createOneSeatedShip() {

        return new OneSeatedShip();
    }

    public static TwoSeatedShip createTwoSeatedShip(){
        return new TwoSeatedShip();
    }

    public static ThreeSeatedShip createThreeSeatedShip(){
        return new ThreeSeatedShip();
    }

    public static FourSeatedShip createFourSeatedShip(){
        return new FourSeatedShip();
    }
}
