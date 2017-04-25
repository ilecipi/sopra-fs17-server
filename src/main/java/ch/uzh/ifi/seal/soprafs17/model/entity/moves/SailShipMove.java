package ch.uzh.ifi.seal.soprafs17.model.entity.moves;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by tonio99tv on 28/03/17.
 */
@Entity
public class SailShipMove extends AMove {

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

    @OneToOne
    private AShip ship;

    public AShip getShip() {
        return ship;
    }

    public void setShip(AShip ship) {
        this.ship = ship;
    }

    @OneToOne
    private SiteBoard siteBoard;

    public SiteBoard getSiteBoard() {
        return siteBoard;
    }

    public void setSiteBoard(SiteBoard siteBoard) {
        this.siteBoard = siteBoard;
    }

    public SailShipMove() {
    }

    public SailShipMove(Game game, User user, AShip ship, Round round, SiteBoard siteBoard) {
        super(user, game, round);
        this.ship = ship;
        this.siteBoard = siteBoard;
    }

    @Override
    public Game makeMove(Game game) {
        return sailShip(game);
    }

    /*TODO IN VERIFIER
    * -check ship is in roundList (Not yet removed)
    * -check Siteboard available (No shipped docked)
    * */
    private Game sailShip(Game game) {
        this.ship.setDocked(true);
        ship.setSiteBoard(this.siteBoard);
        this.siteBoard.setOccupied(true);
        siteBoard.setDockedShip(this.ship);
        if (super.getRound().getIsActionCardSail() == 1) {
            super.getRound().setIsActionCardSail(0);
        }
        return game;
    }
}
