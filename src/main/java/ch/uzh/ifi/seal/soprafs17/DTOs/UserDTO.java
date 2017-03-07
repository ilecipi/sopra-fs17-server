package ch.uzh.ifi.seal.soprafs17.DTOs;

/**
 * Created by ilecipi on 06.03.17.
 */
public class UserDTO {

    public String name;
    public String token;
    public String username;
    public String color;


    public UserDTO(String name, String token, String username, String color) {
        this.name = name;
        this.token = token;
        this.username = username;
        this.color = color;
    }

}
