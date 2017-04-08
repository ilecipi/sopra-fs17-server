package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

/**
 * Created by tonio99tv on 07/04/2017.
 */

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.MarketCard;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("market")
public class Market extends SiteBoard{

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @OneToOne
    private Game game;


//    @ElementCollection
//    private List<MarketCard> marketCards = new ArrayList<>();

    //default constructor
    public Market(){

    }

    //constructor
    public Market(Game game){
        this.game = game;
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

//    public List<MarketCard> getMarketCards() {
//        return marketCards;
//    }

//    public void setMarketCards(List<MarketCard> marketCards) {
//        this.marketCards = marketCards;
//    }

}