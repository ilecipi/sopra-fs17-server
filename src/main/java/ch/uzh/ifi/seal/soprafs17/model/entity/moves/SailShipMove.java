package ch.uzh.ifi.seal.soprafs17.model.entity.moves;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by tonio99tv on 28/03/17.
 */
@Entity
public class SailShipMove extends Move {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private AShip ship;

    @OneToOne
    private Temple temple;

    public SailShipMove(){}
    public SailShipMove(Game game,User user, AShip ship,Round round, Temple temple){
        super(user,game,round);
        this.ship = ship;
        this.temple = temple;
    }

    @Override
    public Game makeMove(Game game){
        return sailShip(game);
    }

    /*TODO IN VERIFIER
    * -check ship is in roundList (Not yet removed)
    * -check Siteboard available (No shipped docked)
    * */
    private Game sailShip(Game game){
            this.ship.setDocked(true);
            ship.setSiteBoard(this.temple);
            this.temple.setOccupied(true);
            temple.setDockedShip(this.ship);
        return game;
    }
}
