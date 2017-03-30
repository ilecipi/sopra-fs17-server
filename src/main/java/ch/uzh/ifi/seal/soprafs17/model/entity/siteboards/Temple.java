package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by erion on 20.03.17.
 */
@Entity
//@DiscriminatorValue("temple")
public class Temple extends StoneBoard implements Serializable {

    public Temple(){}

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Stone[] stones;

    @Column
    int addedStones=0;

    @OneToOne
    private Game game;


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    //Constructor
    public Temple(int users){
        setStones(this.stones,users);
    }


    public Stone[] getStones() {
        return stones;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setStones(Stone[] stones,int users) {
        if(users == 2){
            this.stones=new Stone[4];
        }else if(users == 3 || users == 4){
            this.stones=new Stone[5];
        }
    }


    @Override
    public Map<Long, Integer> countAfterMove() {
        return null;
    }

    @Override
    public Map<Long, Integer> countEndOfRound() {
        return null;
    }

    @Override
    public Map<Long, Integer> countEndOfGame() {
        return null;
    }

    @Override
    public void addStone(Stone stone) {
        System.out.println(addedStones);
        if(addedStones == stones.length-1){
            stones[addedStones++]=stone;
            addedStones= addedStones%stones.length;
        }else {
            stones[addedStones++] = stone;
        }
    }
}
