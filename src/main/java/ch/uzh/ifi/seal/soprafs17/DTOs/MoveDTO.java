package ch.uzh.ifi.seal.soprafs17.DTOs;

import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.User;

/**
 * Created by erion on 08.03.17.
 */
public class MoveDTO {

    private static final long serialVersionUID = 1L;

    public Long id;

    public Game game;

    public User user;


    public MoveDTO(Long id, Game game, User user){
        this.id=id;
        this.game=game;
        this.user=user;
    }
}
