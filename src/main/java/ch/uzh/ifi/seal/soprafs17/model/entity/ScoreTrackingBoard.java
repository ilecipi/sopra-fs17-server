package ch.uzh.ifi.seal.soprafs17.model.entity;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tonio99tv on 04/04/17.
 */
public class ScoreTrackingBoard {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Game game;

    @ElementCollection
    private Map< Long,Integer > scoreBoard = new HashMap<Long,Integer>();

    public void collectPoints(){
    //...
    }
}
