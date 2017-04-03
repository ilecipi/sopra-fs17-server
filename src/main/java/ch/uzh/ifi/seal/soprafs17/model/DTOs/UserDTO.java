package ch.uzh.ifi.seal.soprafs17.model.DTOs;

import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;

import java.util.List;

/**
 * Created by ilecipi on 01.04.17.
 */
public class UserDTO {
    UserDTO(){}

    public UserDTO(Long id, String name, String username, String token, UserStatus status, List<Long> games, List<Long> moves, String color) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.token = token;
        this.status = status;
        this.games = games;
        this.moves = moves;
        this.color = color;
    }

    public Long id;
    public String name;
    public String username;
    public String token;
    public UserStatus status;
    public List<Long> games;
    public List<Long> moves;
    public String color;
}
