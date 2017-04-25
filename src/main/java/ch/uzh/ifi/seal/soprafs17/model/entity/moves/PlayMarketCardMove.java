package ch.uzh.ifi.seal.soprafs17.model.entity.moves;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.*;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Market;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilecipi on 13.04.17.
 */
@Entity
public class PlayMarketCardMove extends AMove {

    public PlayMarketCardMove() {
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

    public AMarketCard getaMarketCard() {
        return aMarketCard;
    }

    public void setaMarketCard(AMarketCard aMarketCard) {
        this.aMarketCard = aMarketCard;
    }

    @OneToOne
    private AMarketCard aMarketCard;


    public PlayMarketCardMove(User user, Round round, Game game, AMarketCard aMarketCard) {
        super(user, game, round);
        this.aMarketCard = aMarketCard;
    }

    @Override
    public Game makeMove(Game game) {
        if (this.aMarketCard instanceof Sarcophagus) {
            game.getBurialChamber().addStone(new Stone(super.getUser().getColor()));
            super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - 1);
            super.getRound().setImmediateCard(false);
            super.getUser().getMarketCards().remove(aMarketCard);
            aMarketCard.setUser(null);
        } else if (this.aMarketCard instanceof Entrance) {
            game.getPyramid().addStone(new Stone(super.getUser().getColor()));
            game.setEntranceCardIsUsed();
            super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - 1);
            super.getRound().setImmediateCard(false);
            super.getUser().getMarketCards().remove(aMarketCard);
            aMarketCard.setUser(null);
        } else if (this.aMarketCard instanceof PavedPath) {
            game.getObelisk().addStone(new Stone(super.getUser().getColor()));
            super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - 1);
            super.getRound().setImmediateCard(false);
            super.getUser().getMarketCards().remove(aMarketCard);
            aMarketCard.setUser(null);
        } else if (this.aMarketCard instanceof Hammer) {
            super.getRound().setActionCardHammer(true);
            //Give the right amount of stones
            if (super.getUser().getSupplySled() == 3) {
                super.getUser().setSupplySled(5);
                super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - 2);
            } else if (super.getUser().getSupplySled() == 4) {
                super.getUser().setSupplySled(5);
                super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - 1);
            } else if (super.getUser().getSupplySled() == 5) {
                super.getUser().setSupplySled(5);
            } else {
                super.getUser().setSupplySled(super.getUser().getSupplySled() + 3);
                super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - 3);
            }
            super.getUser().getMarketCards().remove(aMarketCard);
            aMarketCard.setUser(null);
        } else if (this.getaMarketCard() instanceof Sail) {
            super.getRound().setIsActionCardSail(2);
            aMarketCard.setUser(null);
        } else if (this.getaMarketCard() instanceof Chisel){
            super.getRound().setIsActionCardChisel(2);
            aMarketCard.setUser(null);
        } else if (this.getaMarketCard() instanceof Lever){
            super.getRound().setActionCardLever(true);
            List<String> tmp = new ArrayList<>();
            tmp.add("played");
            super.getRound().setListActionCardLever(tmp);
            aMarketCard.setUser(null);
        }
        game.setDiscardedCardsCounter(game.getDiscardedCardsCounter()+1);
        return game;
    }
}
