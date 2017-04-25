package ch.uzh.ifi.seal.soprafs17.model.entity.moves;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.MCImmediate;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.Sarcophagus;
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
    public GiveCardToUserMove() {
    }

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

    public int getPosition() {
        return position;
    }

    @Column
    private int position;

    public GiveCardToUserMove(User user, Round round, Game game, int position) {
        super(user, game, round);
        this.position = position;
    }

    @Override
    public Game makeMove(Game game) {
        List<SiteBoard> siteBoards = game.getSiteBoards();
        Market market = null;
        if (!siteBoards.isEmpty()) {
            for (SiteBoard s : siteBoards) {
                if (s.getDiscriminatorValue().equals("market")) {
                    market = (Market) s;
                }
            }
        }

        market.getUserColor().remove(0);
        AMarketCard cardToTake = market.getMarketCards().get(position);
        if (cardToTake instanceof MCImmediate) {
            super.getRound().setImmediateCard(true);
        }
        cardToTake.setTaken(true);
        super.getUser().getMarketCards().add(cardToTake);
        cardToTake.setUser(super.getUser());

        return game;
    }
}
