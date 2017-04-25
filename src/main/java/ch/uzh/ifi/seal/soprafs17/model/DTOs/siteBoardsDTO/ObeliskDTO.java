package ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO;

import java.util.Map;

/**
 * Created by erion on 06.04.17.
 */
public class ObeliskDTO {
    public Long id;
    public Map<String, Integer> obelisks;
    boolean isOccupied;
    public ObeliskDTO() {
    }

    public ObeliskDTO(Long id, Map<String, Integer> obelisks, boolean isOccupied) {
        this.id = id;
        this.obelisks = obelisks;
        this.isOccupied = isOccupied;
    }
}
