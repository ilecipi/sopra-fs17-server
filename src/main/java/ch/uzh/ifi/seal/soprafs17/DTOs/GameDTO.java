package ch.uzh.ifi.seal.soprafs17.DTOs;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.User;

/**
 * Created by erion on 07.03.17.
 */
public class GameDTO {

    public Long id;
    public String name;
    public String owner;
    public GameStatus status;
    public User currentPlayer;

    public GameDTO(Long id, String name, String owner, GameStatus status, User currentPlayer){
        this.id=id;
        this.name=name;
        this.owner=owner;
        this.status=status;
        this.currentPlayer=currentPlayer;
    }
}
