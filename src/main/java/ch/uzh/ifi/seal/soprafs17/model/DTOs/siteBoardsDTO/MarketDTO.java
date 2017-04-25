package ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO;

import java.util.List;

/**
 * Created by tonio99tv on 08/04/17.
 */
public class MarketDTO {
    public Long id;
    public boolean isOccupied;
    public List<String> currentCards;
    public List<String> userColors;
    public MarketDTO() {
    }

    public MarketDTO(Long id, boolean isOccupied, List<String> currentCards, List<String> userColors) {
        this.id = id;
        this.isOccupied = isOccupied;
        this.currentCards = currentCards;
        this.userColors = userColors;

    }
}
