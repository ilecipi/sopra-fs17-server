package ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;

import java.util.List;

/**
 * Created by erion on 04.04.17.
 */
public class TempleDTO extends StoneBoardDTO{

    public Long id;
    public Stone[] stones;
    public Long game;
    public boolean isOccupied;

    public TempleDTO(){}

    public TempleDTO(Long id, Stone[] stones, Long gameId, boolean isOccupied){
        this.id=id;
        this.stones=stones;
        this.game=gameId;
        this.isOccupied=isOccupied;
    }
}
