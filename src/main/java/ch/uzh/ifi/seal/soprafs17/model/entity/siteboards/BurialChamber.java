package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by erion on 20.03.17.
 */
@Entity
@DiscriminatorValue("burialChamber")
public class BurialChamber extends StoneBoard implements Serializable{

    @Transient
    public String getDiscriminatorValue() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

    @Id
    @GeneratedValue
    private Long id;
    @ElementCollection
    private List<Stone> firstRow = new ArrayList<>();

    @ElementCollection
    private List<Stone> secondRow = new ArrayList<>();

    @ElementCollection
    private List<Stone> thirdRow = new ArrayList<>();

    private int columnCounter = 0;


    public BurialChamber(){}
    @Override
    public void addStone(Stone stone) {
            if(firstRow.size()==columnCounter){
                firstRow.add(stone);
            }else if(secondRow.size()==columnCounter){
                secondRow.add(stone);
            }else{
                thirdRow.add(stone);
                columnCounter++;
            }
    }


    @Override
    public Map<String, Integer> countAfterMove() {
        return null;
    }

    @Override
    public Map<String, Integer> countEndOfRound() {
        return null;
    }

    @Override
    public Map<String, Integer> countEndOfGame() {
        return null;
    }
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public List<Stone> getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(List<Stone> firstRow) {
        this.firstRow = firstRow;
    }

    public List<Stone> getSecondRow() {
        return secondRow;
    }

    public void setSecondRow(List<Stone> secondRow) {
        this.secondRow = secondRow;
    }

    public List<Stone> getThirdRow() {
        return thirdRow;
    }

    public void setThirdRow(List<Stone> thirdRow) {
        this.thirdRow = thirdRow;
    }

}
