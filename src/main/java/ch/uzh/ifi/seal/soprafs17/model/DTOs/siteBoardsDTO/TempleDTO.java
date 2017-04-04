package ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;

/**
 * Created by erion on 04.04.17.
 */
public class TempleDTO extends StoneBoardDTO{

    public Long id;
    public Stone[] stones;
    public int addedStones=0;
    public Long game;
    public boolean isOccupied;



    public TempleDTO(Long id, Stone[] stones, int addedStones, Long gameId,boolean isOccupied){
        this.id=id;
        this.stones=stones;
        this.addedStones=addedStones;
        this.game=gameId;
        this.isOccupied=isOccupied;
    }
}
