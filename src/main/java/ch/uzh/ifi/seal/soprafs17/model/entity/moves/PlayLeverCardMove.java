package ch.uzh.ifi.seal.soprafs17.model.entity.moves;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by ilecipi on 13.04.17.
 */
@Entity
public class PlayLeverCardMove extends AMove {

    @Column
    Stone[] stones;
    @Id
    @GeneratedValue
    private Long id;

    public PlayLeverCardMove() {
    }

    public PlayLeverCardMove(User user, Round round, Game game, Stone[] stones) {
        super(user, game, round);
        this.stones = stones;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Stone[] getStones() {
        return stones;
    }

    public void setStones(Stone[] stones) {
        this.stones = stones;
    }

    @Override
    public Game makeMove(Game game) {
        super.getRound().setStonesLeverCard(this.stones);
        return game;
    }
}
