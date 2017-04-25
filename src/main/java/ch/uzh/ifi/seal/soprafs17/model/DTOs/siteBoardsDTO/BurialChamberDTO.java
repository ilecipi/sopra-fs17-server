package ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;

import java.util.List;

/**
 * Created by erion on 06.04.17.
 */
public class BurialChamberDTO {
    public Long id;
    public List<Stone> firstRow;
    public List<Stone> secondRow;
    public List<Stone> thirdRow;
    public boolean isOccupied;
    public BurialChamberDTO() {
    }

    public BurialChamberDTO(Long id, List<Stone> firstRow, List<Stone> secondRow, List<Stone> thirdRow, boolean isOccupied) {
        this.id = id;
        this.firstRow = firstRow;
        this.secondRow = secondRow;
        this.thirdRow = thirdRow;
        this.isOccupied = isOccupied;
    }
}
