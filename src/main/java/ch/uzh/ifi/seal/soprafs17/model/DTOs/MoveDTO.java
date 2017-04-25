package ch.uzh.ifi.seal.soprafs17.model.DTOs;

/**
 * Created by ilecipi on 01.04.17.
 */
public class MoveDTO {
    public Long id;
    public Long user;
    public Long round;
    public Long game;
    MoveDTO() {
    }
    public MoveDTO(Long id, Long user, Long round, Long game) {
        this.id = id;
        this.user = user;
        this.round = round;
        this.game = game;
    }
}
