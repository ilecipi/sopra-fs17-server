package ch.uzh.ifi.seal.soprafs17.model.entity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tonio99tv on 04/04/17.
 */
@Entity
public class ScoreTrackingBoard {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Game game;

    public Map<Long, Integer> getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(Map<Long, Integer> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    @ElementCollection
    private Map< Long,Integer > scoreBoard = new HashMap<Long,Integer>();

    public void collectPoints(){
    //...
    }
}
