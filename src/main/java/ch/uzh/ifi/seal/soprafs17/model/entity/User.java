package ch.uzh.ifi.seal.soprafs17.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import com.fasterxml.jackson.annotation.*;

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

    @ManyToMany
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    private List<Game> games = new ArrayList<>();

    @OneToMany(mappedBy = "user")
//    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    private List<AMove> AMoves = new ArrayList<>();

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

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

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

    public int getStoneQuarry(){
        return stoneQuarry;
    }

    public void setStoneQuarry(int stoneQuarry){
        this.stoneQuarry = stoneQuarry;
    }
}
