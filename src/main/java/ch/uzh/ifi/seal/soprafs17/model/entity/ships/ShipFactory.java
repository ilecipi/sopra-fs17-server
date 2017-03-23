package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erion on 17.03.17.
 */
@Entity
public class ShipFactory implements Serializable {


    public Stone[] getStones() {
        return stones;
    }

    public void setStones(Stone[] stones) {
        this.stones = stones;
    }

    Stone[] stones;


    int addedStones;
//    public ShipFactory(int users){
//        //4 player ships' cards
//        ships = new ArrayList<ArrayList<ShipFactory>>(){{
//
//
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createOneSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//
//            }});
//            add(new ArrayList<ShipFactory>(){{
//                add(new ShipFactory().createFourSeatedShip());
//                add(new ShipFactory().createThreeSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//                add(new ShipFactory().createTwoSeatedShip());
//
//            }});
//        }};
//    }


    @Id
    @GeneratedValue
    private long id;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

//    public void initializeStones(){};

    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    private Game game;

    public OneSeatedShip createOneSeatedShip(){
        OneSeatedShip oneSeatedShip= new OneSeatedShip();
        oneSeatedShip.setStones(this.stones);
        return oneSeatedShip;
    }

    public ShipFactory createTwoSeatedShip(){
        ShipFactory twoSeatedShip= new TwoSeatedShip();
        twoSeatedShip.setStones(this.stones);
        return twoSeatedShip;
    }

    public ShipFactory createThreeSeatedShip(){
        ShipFactory threeSeatedShip = new ThreeSeatedShip();
        threeSeatedShip.setStones(this.stones);
        return threeSeatedShip;
    }

    public ShipFactory createFourSeatedShip(){
        ShipFactory fourSeatedShip= new FourSeatedShip();
        fourSeatedShip.setStones(this.stones);
        return fourSeatedShip;
    }
}
