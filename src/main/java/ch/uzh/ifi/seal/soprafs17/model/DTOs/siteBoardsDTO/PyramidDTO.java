package ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;

import java.util.List;

/**
 * Created by ilecipi on 05.04.17.
 */
public class PyramidDTO {
    public PyramidDTO(){}
    public Long id;
    public List<Stone> addedStones;
    public boolean isOccupied;


    public PyramidDTO(Long id,List<Stone> addedStones, boolean occupied){
        this.id=id;
        this.addedStones=addedStones;
        this.isOccupied = occupied;
    }
}
