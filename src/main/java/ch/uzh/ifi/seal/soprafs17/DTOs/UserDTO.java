package ch.uzh.ifi.seal.soprafs17.DTOs;

import ch.uzh.ifi.seal.soprafs17.entity.Game;

import java.util.List;

/**
 * Created by ilecipi on 06.03.17.
 */
public class UserDTO {

    public String name;
    public String token;
    public String username;
    public String color;
    private List<Game> games;


    public UserDTO(String name, String token, String username, String color,List<Game> games) {
        this.name = name;
        this.token = token;
        this.username = username;
        this.color = color;
        this.games=games;
    }

}
