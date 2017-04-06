package ch.uzh.ifi.seal.soprafs17.model.entity.moves;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;

import javax.persistence.*;

/**
 * Created by erion on 29.03.17.
 */
@Entity
public class AddStoneToShipMove extends AMove {

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

    @Column
    private int position;

    public AShip getShip() {
        return ship;
    }

    public void setShip(AShip ship) {
        this.ship = ship;
    }

    public int getPosition() {

        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public AddStoneToShipMove(){}
    public AddStoneToShipMove(Game game, User user, AShip ship, int position, Round round){
            super(user,game,round);
            this.ship = ship;
            this.position=position;
    }
    @Override
    public Game makeMove(Game game) {
        return addStone(game);
    }

    public Game addStone(Game game){
        Stone stone = new Stone(super.getUser().getColor());
        ship.addStone(stone,this.position);
        game.findNextPlayer();
        int index = (game.getPlayers().lastIndexOf(game.getCurrentPlayer())+1)%game.getPlayers().size();
        game.setNextPlayer(game.getPlayers().get(index));
        return game;
    }
}
