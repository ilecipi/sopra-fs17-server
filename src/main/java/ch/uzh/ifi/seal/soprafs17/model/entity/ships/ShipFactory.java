package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erion on 17.03.17.
 */
@Entity
public class ShipFactory implements Serializable {

    public ShipFactory(){}
//    public ShipFactory(int users){
//        //4 player Ships' cards
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


    public ShipFactory getShip() {
        return ship;
    }

    public void setShip(ShipFactory ship) {
        this.ship = ship;
    }

    
    public ShipFactory ship = createFourSeatedShip();



    @Id
    @GeneratedValue
    private long id;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @OneToOne
    private Game game;

    public ShipFactory createOneSeatedShip(){
        ShipFactory oneSeatedShip= new OneSeatedShip();
        return oneSeatedShip;
    }

    public ShipFactory createTwoSeatedShip(){
        ShipFactory twoSeatedShip= new OneSeatedShip();
        return twoSeatedShip;
    }

    public ShipFactory createThreeSeatedShip(){
        ShipFactory threeSeatedShip= new OneSeatedShip();
        return threeSeatedShip;
    }

    public ShipFactory createFourSeatedShip(){
        ShipFactory fourSeatedShip= new OneSeatedShip();
        return fourSeatedShip;
    }
}
