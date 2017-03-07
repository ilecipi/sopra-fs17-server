package ch.uzh.ifi.seal.soprafs17.DTOs;

/**
 * Created by ilecipi on 06.03.17.
 */
public class UserDTO {

    public String name;
    public String token;
    public String nickname;
    public String color;


    public UserDTO(String name, String token, String nickname, String color) {
        this.name = name;
        this.token = token;
        this.nickname = nickname;
        this.color = color;
    }

    public UserDTO() {

    }
}
