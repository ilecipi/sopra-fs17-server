package ch.uzh.ifi.seal.soprafs17.model.DTOs;

import java.util.List;

/**
 * Created by ilecipi on 01.04.17.
 */
public class RoundDTO {
    RoundDTO(){}

    public RoundDTO(Long id, Long game, List<Long> moves, List<Long> ships) {
        this.id = id;
        this.game = game;
        this.moves = moves;
        this.ships = ships;
    }

    public Long id;
    public Long game;
    public List<Long> moves;
    public List<Long> ships;
}
