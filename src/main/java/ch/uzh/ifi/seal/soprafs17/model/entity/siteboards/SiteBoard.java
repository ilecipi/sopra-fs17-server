package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

/**
 * Created by erion on 20.03.17.
 */
@Entity
public abstract class SiteBoard {


    @Id
    @GeneratedValue
    private Long id;

    @Column
    private boolean isOccupied;

    public AShip getDockedShip() {
        return dockedShip;
    }

    public void setDockedShip(AShip dockedShip) {
        this.dockedShip = dockedShip;
    }

    //should it be private with get... or public? for client
    private AShip dockedShip;

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    @OneToOne
//    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    Game game;

}
