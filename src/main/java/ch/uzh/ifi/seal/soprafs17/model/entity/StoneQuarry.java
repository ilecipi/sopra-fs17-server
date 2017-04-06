package ch.uzh.ifi.seal.soprafs17.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;

/**
 * Created by liwitz on 05.04.17.
 */
@Entity
public class StoneQuarry implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    private Map<String, Integer> availableStones = new HashMap<String, Integer>();

    private Stone stone;

    public void StoneQuarry(){
        reset();
    }

    public void reset(){
        availableStones.put("black", 30);
        availableStones.put("white", 30);
        availableStones.put("brown", 30);
        availableStones.put("grey", 30);
    }

    public boolean canGetStone(String color){
        if((color == "black" || color == "white" || color == "brown" || color == "grey") && availableStones.get(color)>=0){
            stone.setColor(color);
            availableStones.put(color, availableStones.get(color)-1);
            return true;
        } else {
            return false;
        }
    }

}
