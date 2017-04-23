package ch.uzh.ifi.seal.soprafs17.model.entity.moves;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ilecipi on 13.04.17.
 */
@Entity
public class PlayLeverCardMove extends AMove {

    public PlayLeverCardMove() {
    }

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


    public List<String> getUserColors() {
        return userColors;
    }

    public void setUserColors(List<String> userColors) {
        this.userColors = userColors;
    }

    @ElementCollection
    private List<String> userColors;


    public PlayLeverCardMove(User user, Round round, Game game, List<String> userColors) {
        super(user, game, round);
        this.userColors = userColors;
    }

    @Override
    public Game makeMove(Game game) {
//        super.getRound().setActionCardLever(false);
        return game;
    }
}
