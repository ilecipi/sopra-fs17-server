package ch.uzh.ifi.seal.soprafs17.model.entity.ships;

/**
 * Created by erion on 17.03.17.
 */
public class ShipFactory  {

    public IShip createOneSeatedShip(){
        IShip oneSeatedShip= new OneSeatedShip();
        return oneSeatedShip;
    }

    public IShip createTwoSeatedShip(){
        IShip twoSeatedShip= new OneSeatedShip();
        return twoSeatedShip;
    }

    public IShip createThreeSeatedShip(){
        IShip threeSeatedShip= new OneSeatedShip();
        return threeSeatedShip;
    }

    public IShip createFourSeatedShip(){
        IShip fourSeatedShip= new OneSeatedShip();
        return fourSeatedShip;
    }
}
