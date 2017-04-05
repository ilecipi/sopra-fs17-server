package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

/**
 * Created by erion on 20.03.17.
 */
@Entity
@DiscriminatorValue("pyramid")
public class Pyramid extends StoneBoard {

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    private Long id;

    public Game getGame() {
        return game;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    @JsonIgnore
    @OneToOne
    private Game game;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    int counter = 0;

    final int[] points = {2,1,3,2,4,3,2,1,3,2,3,1,3,4};

    @ElementCollection
    public List<Stone> addedStones = new ArrayList<>();

    public String getType() {
        return type;
    }

    private final String type = "immediately";
//    List<Stone> addedStones=new ArrayList<>();
//    private Map<Long,Integer> score = new HashMap<Long,Integer>();

//    @ElementCollection
//    private Map<Stone,Integer> pyramid = new Hashtable<Stone,Integer>(){{
//        put(new Stone(),2);
//        put(new Stone(),1);
//        put(new Stone(),3);
//        put(new Stone(),2);
//        put(new Stone(),4);
//        put(new Stone(),3);
//        put(new Stone(),2);
//        put(new Stone(),1);
//        put(new Stone(),3);
//        put(new Stone(),2);
//        put(new Stone(),3);
//        put(new Stone(),1);
//        put(new Stone(),3);
//        put(new Stone(),4);
//
//    }};



//    private Map<Stone,Integer> secondColumn = new HashMap<Stone,Integer>(){{
//        put(null,2);
//        put(null,4);
//        put(null,3);
//    }};
//
//    private Map<Stone,Integer> thirdColumn = new HashMap<Stone,Integer>(){{
//        put(null,2);
//        put(null,1);
//        put(null,3);
//    }};
//
//    private Map<Stone,Integer> secondLevelFirstColumn = new HashMap<Stone,Integer>(){{
//        put(null,2);
//        put(null,3);
//    }};
//
//    private Map<Stone,Integer> secondLevelSecondColumn = new HashMap<Stone,Integer>(){{
//        put(null,1);
//        put(null,3);
//    }};
//
//    private Map<Stone,Integer> thirdLevel = new HashMap<Stone,Integer>(){{
//        put(null,4);
//    }};


    @Override
    public void addStone(Stone stone) {
        this.addedStones.add(stone);
        if(stone!=null) {
            this.counter++;
        }

    }

    @Override
    public Map<String,Integer> countAfterMove() {
        Map<String, Integer> point = fillPoints();
        for(int i=0;i<counter;i++){
            String s = addedStones.get(i).getColor();
            int currentPoint = point.get(s);
            if(addedStones.get(i)!=null&&points.length>i) {

                int tmpPoint = points[i] + currentPoint;
                point.put(s,tmpPoint);
            }else{
                int tmpPoint = 1 + currentPoint;
                point.put(s,tmpPoint);
            }

        }
        return point;
    }

    private HashMap<String, Integer> fillPoints() {
        return new HashMap<String,Integer>(){{
            put("black",0);
            put("gray",0);
            put("white",0);
            put("brown",0);
        }};
    }

    @Override
    public Map<Long, Integer> countEndOfRound() {
        return null;
    }

    @Override
    public Map<Long, Integer> countEndOfGame() {
        return null;
    }

    @Transient
    public String getDiscriminatorValue() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }
}
