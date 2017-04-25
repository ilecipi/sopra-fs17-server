package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

/**
 * Created by tonio99tv on 07/04/2017.
 */

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("market")
public class Market extends SiteBoard {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @OneToOne
    private Game game;


    @ElementCollection
    private List<AMarketCard> marketCards = new ArrayList<>();
    @ElementCollection
    private List<String> userColor = new ArrayList<>();

    //default constructor
    public Market() {

    }

    //constructor
    public Market(Game game) {
        this.game = game;
    }

    public List<String> getUserColor() {
        return userColor;
    }

    public void setUserColor(List<String> userColor) {
        this.userColor = userColor;
    }

    @Transient
    public String getDiscriminatorValue() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }


    public List<AMarketCard> getMarketCards() {
        return marketCards;
    }

    public void setMarketCards(List<AMarketCard> marketCards) {
        this.marketCards = marketCards;
    }

    public void addUser(String color) {
        this.userColor.add(color);
    }
}