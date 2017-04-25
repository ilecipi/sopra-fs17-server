package ch.uzh.ifi.seal.soprafs17.model.entity;

import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erion on 20.03.17.
 */
@Entity
public class Round implements Serializable {


    @Id
    @GeneratedValue
    private Long id;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @OneToOne
    @JoinColumn(name = "GAME_ID")
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    private Game game;

    public List<AMove> getAMoves() {
        return AMoves;
    }

    public void setAMoves(List<AMove> AMoves) {
        this.AMoves = AMoves;
    }

    @OneToMany(mappedBy = "round")
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    private List<AMove> AMoves;

    public List<AShip> getShips() {
        return ships;
    }

    public void setShips(List<AShip> ships) {
        this.ships = ships;
    }

    //TODO: REVISE WHETHER WE NEED mappedBy or JoinColumn
    @OneToMany
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    private List<AShip> ships;

    public List<AMarketCard> getMarketCards() {
        return marketCards;
    }

    public void setMarketCards(List<AMarketCard> marketCards) {
        this.marketCards = marketCards;
    }

    @OneToMany
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    private List<AMarketCard> marketCards;

    private boolean isActionCardHammer;

    private int isActionCardChisel;

    private int isActionCardSail;

    public boolean isActionCardLever() {
        return isActionCardLever;
    }

    public void setActionCardLever(boolean actionCardLever) {
        isActionCardLever = actionCardLever;
    }

    public List<String> getUserColorsLeverCard() {
        return userColorsLeverCard;
    }

    public void setUserColorsLeverCard(List<String> userColorsLeverCard) {
        this.userColorsLeverCard = userColorsLeverCard;
    }

    public Stone[] getStonesLeverCard() {
        return stonesLeverCard;
    }

    public void setStonesLeverCard(Stone[] stonesLeverCard) {
        this.stonesLeverCard = stonesLeverCard;
    }

    @Column
    Stone[] stonesLeverCard;

    @ElementCollection
    private List<String> userColorsLeverCard = new ArrayList<>();

    @JsonIgnore
    private boolean isActionCardLever = false;

    public List<String> getListActionCardLever() {
        return ListActionCardLever;
    }

    public void setListActionCardLever(List<String> listActionCardLever) {
        ListActionCardLever = listActionCardLever;
    }

    @ElementCollection
    private List<String> ListActionCardLever = new ArrayList<>();

    @JsonIgnore
    @Column
    private boolean immediateCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isImmediateCard() {
        return immediateCard;
    }

    public boolean isActionCardHammer() {
        return isActionCardHammer;
    }

    public void setActionCardHammer(boolean actionCardHammer) {
        isActionCardHammer = actionCardHammer;
    }

    public int getIsActionCardChisel() {
        return isActionCardChisel;
    }

    public void setIsActionCardChisel(int isActionCardChisel) {
        this.isActionCardChisel = isActionCardChisel;
    }

    public int getIsActionCardSail() {
        return isActionCardSail;
    }

    public void setIsActionCardSail(int isActionCardSail) {
        this.isActionCardSail = isActionCardSail;
    }

    public void setImmediateCard(boolean immediateCard) {
        this.immediateCard = immediateCard;
    }

    public Round() {
    }
}
