package ch.uzh.ifi.seal.soprafs17.model.DTOs.siteBoardsDTO;

import java.util.List;

/**
 * Created by tonio99tv on 08/04/17.
 */
public class MarketDTO {

    public Long id;
    //public List<MarketCard> marketCards;
    public boolean isOccupied;

    public MarketDTO(Long id, boolean isOccupied){
        //public MarketDTO(Long id, List<MarketCard> marketCards ,boolean isOccupied)
        this.id = id;
        //this.marketCards = marketCards;
        this.isOccupied = isOccupied;
    }
}
