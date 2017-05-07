package ch.uzh.ifi.seal.soprafs17.model.DTOs;

import java.util.List;

/**
 * Created by ilecipi on 01.04.17.
 */
public class RoundDTO {

    public Long id;
    public Long game;
    public List<Long> moves;
    public List<Long> ships;
    public List<Long> marketCards;
    public boolean immediateCard;
    RoundDTO(){}

    public RoundDTO(Long id, Long game, List<Long> moves, List<Long> ships, List<Long> marketCards, boolean immediateCard) {
        this.id = id;
        this.game = game;
        this.moves = moves;
        this.ships = ships;
        this.marketCards = marketCards;
        this.immediateCard = immediateCard;
    }

}
