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

    private final String type = "endOfRound";
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Stone[] stones;
    @Column
    private int insertIndex = 0;
    @Column
    private int completedRows = 0;
    @JsonIgnore
    private int addedStones = 0;

    public Temple() {
    }

    //Constructor
    public Temple(int users) {
        setStones(this.stones, users);
    }

    @Transient
    public String getDiscriminatorValue() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

    public int getInsertIndex() {
        return insertIndex;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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


    public void setStones(Stone[] stones, int users) {
        if (users == 2) {
            this.stones = new Stone[4];
        } else if (users == 3 || users == 4) {
            this.stones = new Stone[5];
        }
    }


    @Override
    public Map<String, Integer> countAfterMove() {
        return null;
    }

    @Override
    public Map<String, Integer> countEndOfRound() {
        Map<String, Integer> points = fillPoints();
        for (Stone s : stones) {
            if (s != null) {
                int previousPoints = points.get(s.getColor());
                points.put(s.getColor(), previousPoints + 1);
            }
        }
        return points;
    }

    @Override
    public Map<String, Integer> countEndOfGame() {
        return null;
    }

    @Override
    public void addStone(Stone stone) {
        if (insertIndex == stones.length - 1) {
            stones[insertIndex++] = stone;
            completedRows++;
            insertIndex = insertIndex % stones.length;
            addedStones++;
        } else {
            stones[insertIndex++] = stone;
            addedStones++;
        }
    }

    private HashMap<String, Integer> fillPoints() {
        return new HashMap<String, Integer>() {{
            put("black", 0);
            put("grey", 0);
            put("white", 0);
            put("brown", 0);
        }};
    }

    public int getCompletedRows() {
        return completedRows;
    }

    public int getAddedStones() {
        return addedStones;
    }
}
