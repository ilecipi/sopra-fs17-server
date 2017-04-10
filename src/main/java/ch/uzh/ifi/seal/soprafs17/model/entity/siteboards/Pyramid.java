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
    public Pyramid(){}
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
            for (int i = 0; i < this.counter; i++) {
                if (this.addedStones.get(i) != null && this.addedStones.get(i).getColor() != null) {
                    String s = this.addedStones.get(i).getColor();
                    if (point.get(s) != null) {
                        int currentPoint = point.get(s);
                        if (this.addedStones.get(i) != null && this.points.length > i) {
                            //sum the old point with the current points for the player
                            int tmpPoint = this.points[i] + currentPoint;
                            point.put(s, tmpPoint);
                        } else if (this.addedStones.get(i) != null) {
                            //If the pyramid is full, give 1 point to each stone added
                            int tmpPoint = 1 + currentPoint;
                            point.put(s, tmpPoint);
                        }
                    }
                }
            }
                return point;

    }

    private HashMap<String, Integer> fillPoints() {
        return new HashMap<String,Integer>(){{
            put("black",0);
            put("white",0);
            put("brown",0);
            put("grey",0);
        }};
    }

    @Override
    public Map<String, Integer> countEndOfRound() {
        return null;
    }

    @Override
    public Map<String, Integer> countEndOfGame() {
        return null;
    }

    @Transient
    public String getDiscriminatorValue() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }
}
