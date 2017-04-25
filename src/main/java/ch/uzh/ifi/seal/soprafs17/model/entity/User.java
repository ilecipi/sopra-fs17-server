package ch.uzh.ifi.seal.soprafs17.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Proxy;

@Entity
public class User implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private UserStatus status;

    @Column
    private int SupplySled;

    @Column
    private int stoneQuarry;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<AMarketCard> marketCards;

    @ManyToMany
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    private List<Game> games = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<AMove> AMoves;

    public List<AMarketCard> getMarketCards() {
        return marketCards;
    }

    public void setMarketCards(List<AMarketCard> marketCards) {
        this.marketCards = marketCards;
    }


    @Column(nullable = true)
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @JsonIgnore
    public List<AMove> getAMoves() {
        return AMoves;
    }

    public void setAMoves(List<AMove> AMoves) {
        this.AMoves = AMoves;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public int getSupplySled() {
        return SupplySled;
    }

    public void setSupplySled(int supplySled) {
        SupplySled = supplySled;
    }

    public int getStoneQuarry() {
        return stoneQuarry;
    }

    public void setStoneQuarry(int stoneQuarry) {
        this.stoneQuarry = stoneQuarry;
    }
}
