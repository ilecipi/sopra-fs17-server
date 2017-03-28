package ch.uzh.ifi.seal.soprafs17.model.entity.moves;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;

/**
 * Created by tonio99tv on 28/03/17.
 */
public class SailShipMove extends Move {

    User user;

    AShip ship;

    SiteBoard siteBoard;

    public SailShipMove(User user, AShip ship, SiteBoard siteBoard){
        this.user = user;
        this.ship = ship;
        this.siteBoard = siteBoard;
    }

    public Game makeMove(Game game){

        return sailShip(game);
    }

    /*TODO IN VERIFIER
    * -check ship is in roundList (Not yet removed)
    * -check Siteboard available (No shipped docked)
    * */
    private Game sailShip(Game game){
        Round currentRound = game.getCurrentRound();
        currentRound.getShips().remove(this.ship);
        this.siteBoard.setDockedShip(this.ship);

        return game;
    }
}
