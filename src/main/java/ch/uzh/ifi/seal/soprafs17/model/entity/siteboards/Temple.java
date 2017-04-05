package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by erion on 20.03.17.
 */
@Entity
@DiscriminatorValue("temple")
public class Temple extends StoneBoard implements Serializable {

    public Temple(){}

    @Transient
    public String getDiscriminatorValue() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Stone[] stones;

    public int getAddedStones() {
        return addedStones;
    }

    @Column
    int addedStones=0;

    private final String type = "endOfRound";


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    //Constructor
    public Temple(int users){
        setStones(this.stones,users);
    }


    public Stone[] getStones() {
        return stones;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setStones(Stone[] stones,int users) {
        if(users == 2){
            this.stones=new Stone[4];
        }else if(users == 3 || users == 4){
            this.stones=new Stone[5];
        }
    }


    @Override
    public Map<String, Integer> countAfterMove() {

        return null;
    }

    @Override
    public Map<String, Integer> countEndOfRound() {
        Map<String, Integer> points = fillPoints();
        for(Stone s : stones){
            if(s!=null){
                points.put(s.getColor(),1);
            }
        }
        return points;
    }

    @Override
    public Map<Long, Integer> countEndOfGame() {
        return null;
    }

    @Override
    public void addStone(Stone stone) {
        System.out.println(addedStones);
        if(addedStones == stones.length-1){
            stones[addedStones++]=stone;
            addedStones= addedStones%stones.length;
        }else {
            stones[addedStones++] = stone;
        }
    }

    private HashMap<String, Integer> fillPoints() {
        return new HashMap<String,Integer>(){{
            put("black",0);
            put("grey",0);
            put("white",0);
            put("brown",0);
        }};
    }
}
