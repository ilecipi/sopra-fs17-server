package ch.uzh.ifi.seal.soprafs17.model.DTOs;

import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilecipi on 01.04.17.
 */
public class UserDTO {
    UserDTO(){}


    public UserDTO(Long id, String name, String username, String token, UserStatus status, List<Long> games, List<Long> moves, String color,int supplySled,
                    List<AMarketCard> cards,int stoneQuarry) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.token = token;
        this.status = status;
        this.games = games;
        this.moves = moves;
        this.color = color;
        this.supplySled=supplySled;
        this.stoneQuarry = stoneQuarry;
        List<String> playerCardsDTO = new ArrayList<>();
        for(AMarketCard aMarketCard: cards){
            playerCardsDTO.add(aMarketCard.getCardType() +"-"+aMarketCard.getId());
        }
        this.cards=playerCardsDTO;

        }

    public Long id;
    public String name;
    public String username;
    public String token;
    public UserStatus status;
    public List<Long> games;
    public List<Long> moves;
    public String color;
    public int supplySled;
    public int stoneQuarry;
    public List<String> cards;
}
