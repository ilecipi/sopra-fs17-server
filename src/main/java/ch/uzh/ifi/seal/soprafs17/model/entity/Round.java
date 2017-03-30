package ch.uzh.ifi.seal.soprafs17.model.entity;

import ch.uzh.ifi.seal.soprafs17.model.entity.moves.Move;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by erion on 20.03.17.
 */
@Entity
public class Round implements Serializable{



    @Id
    @GeneratedValue
    private Long id;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @OneToOne
    @JoinColumn(name="GAME_ID")
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    private Game game;

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    @OneToMany(mappedBy = "round")
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    private List<Move> moves;

    public List<AShip> getShips() {
        return ships;
    }

    public void setShips(List<AShip> ships) {
        this.ships = ships;
    }

    //TODO: REVISE WHETHER WE NEED mappedBy or JoinColumn
    @OneToMany
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    private List<AShip> ships;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
