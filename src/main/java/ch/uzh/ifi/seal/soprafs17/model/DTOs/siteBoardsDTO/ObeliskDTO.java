package ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO;

import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by erion on 06.04.17.
 */
public class ObeliskDTO {
    public ObeliskDTO(){}
    public Long id;
    public Map<String,Integer> obelisks;
    boolean isOccupied;

    public ObeliskDTO(Long id, Map<String, Integer> obelisks, boolean isOccupied) {
        this.id = id;
        this.obelisks = obelisks;
        this.isOccupied = isOccupied;
    }
}
