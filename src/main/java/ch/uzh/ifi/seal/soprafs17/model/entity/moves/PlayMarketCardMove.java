package ch.uzh.ifi.seal.soprafs17.model.entity.moves;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.Entrance;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.PavedPath;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.Sarcophagus;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Market;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ilecipi on 13.04.17.
 */
@Entity
public class PlayMarketCardMove extends AMove {

    public PlayMarketCardMove(){}
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
    AMarketCard aMarketCard;


    public PlayMarketCardMove(User user, Round round, Game game, AMarketCard aMarketCard){
        super(user,game, round);
        this.aMarketCard= aMarketCard;
    }

    @Override
    public Game makeMove(Game game) {
        if(this.aMarketCard instanceof Sarcophagus){
            game.getBurialChamber().addStone(new Stone(super.getUser().getColor()));
            super.getUser().setStoneQuarry(super.getUser().getStoneQuarry()-1);
            super.getRound().setImmediateCard(false);
            super.getUser().getMarketCards().remove(aMarketCard);
            aMarketCard.setUser(null);
            System.out.println(super.getGame().getRounds().size());
        }
        if(this.aMarketCard instanceof Entrance){
            game.getPyramid().addStone(new Stone(super.getUser().getColor()));
            super.getUser().setStoneQuarry(super.getUser().getStoneQuarry()-1);
            super.getRound().setImmediateCard(false);
            super.getUser().getMarketCards().remove(aMarketCard);
            aMarketCard.setUser(null);
        }
        if(this.aMarketCard instanceof PavedPath){
            game.getObelisk().addStone(new Stone(super.getUser().getColor()));
            super.getUser().setStoneQuarry(super.getUser().getStoneQuarry()-1);
            super.getRound().setImmediateCard(false);
            super.getUser().getMarketCards().remove(aMarketCard);
            aMarketCard.setUser(null);
        }

        return game;
    }
}
