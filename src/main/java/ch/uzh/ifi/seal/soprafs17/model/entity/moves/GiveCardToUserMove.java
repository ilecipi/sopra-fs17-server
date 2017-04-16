package ch.uzh.ifi.seal.soprafs17.model.entity.moves;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Market;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ilecipi on 13.04.17.
 */
@Entity
public class GiveCardToUserMove extends AMove {
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int position;

    public GiveCardToUserMove(User user, Round round, Game game, int position){
        super(user,game,round);
        this.position = position;
    }

    String color;

    User user;
    @Override
    public Game makeMove(Game game) {
        System.out.println("BEFORE FINDING MARKET");
        List<SiteBoard> siteBoards = game.getSiteBoards();
        Market market = null;
        if (!siteBoards.isEmpty()) {
            for (SiteBoard s : siteBoards) {
                if (s.getDiscriminatorValue().equals("market")) {
                    market = (Market) s;
                }
            }
        }
        System.out.println("PRINT MARKET ID: " +market.getId());
        System.out.println("ARRAY COLORS" +market.getUserColor());
        System.out.println("PLAYER COLOR: " + this.getUser().getColor());
        System.out.println("USER NULL? ");
        if(market.getUserColor().size()>0&&market.getUserColor().get(0).equals(this.getUser().getColor())){
            market.getUserColor().remove(0);
            System.out.println("MARKET CARD:   "+ market.getMarketCards());
            System.out.println("USER CARD:     "+this.getUser().getMarketCards());
            AMarketCard cardToTake = market.getMarketCards().remove(position);
            System.out.println("Removed card"+cardToTake.getCardType());

            System.out.println("SIZE MARKET ARRAY: "+market.getMarketCards().size());

            this.getUser().getMarketCards().add(cardToTake);
            cardToTake.setUser(this.user);
            System.out.println("USER CARD AFTER:     "+this.getUser().getMarketCards());
        }
        return game;
    }
}
