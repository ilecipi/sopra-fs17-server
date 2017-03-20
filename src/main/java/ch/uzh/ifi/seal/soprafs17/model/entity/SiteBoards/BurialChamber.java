package ch.uzh.ifi.seal.soprafs17.model.entity.SiteBoards;

import java.util.ArrayList;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;

/**
 * Created by liwitz on 20.03.17.
 */


public class BurialChamber extends StoneBoard implements Countable{

    private ArrayList<Stone> firstRow = null;
    private ArrayList<Stone> secondRow = null;
    private ArrayList<Stone> thirdRow = null;

    public void addStone(Stone stone){

    }

    public ArrayList<Stone> getFirstRow(){
        return firstRow;
    }

    public ArrayList<Stone> getSecondRow(){
        return secondRow;
    }

    public ArrayList<Stone> getThirdRow(){
        return thirdRow;
    }

}