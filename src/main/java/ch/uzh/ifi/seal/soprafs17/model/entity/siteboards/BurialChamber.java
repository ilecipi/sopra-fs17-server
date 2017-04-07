package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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


    private final String Type = "endOfGame";

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

    @ElementCollection
    private Map<String,Integer> pointsOfBurialChamber = new HashMap<String,Integer>(){{
        put("black",0);
        put("white",0);
        put("brown",0);
        put("grey",0);
    }};

    private String[] colors = {"black","white","brown","grey"};

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
        Stone[][] burialChamber = new Stone[3][8];
        for (int i = 0; i < 8; i++) {
            if (firstRow.get(i) != null) {
                burialChamber[0][i] = firstRow.get(i);
            }
            if (secondRow.get(i) != null) {
                burialChamber[1][i] = secondRow.get(i);
            }
            if (thirdRow.get(i) != null) {
                burialChamber[2][i] = thirdRow.get(i);
            }
        }
        for (String c : colors) {
            int connectedStones = 0;
            int oneStoneField=0;
            for (int j = 0; j < 8; j++) {
                for (int i = 0; i < 3; i++) {
                    if (burialChamber[i][j] != null && burialChamber[i][j].getColor().equals(c)) {
                        if (j < 7 && burialChamber[i][j + 1].getColor().equals(c) && !burialChamber[i][j + 1].isCounted()) {
                            connectedStones++;
                            burialChamber[i][j + 1].setCounted();

                        } else if (i < 2 && burialChamber[i + 1][j].getColor().equals(c) && !burialChamber[i + 1][j].isCounted()) {
                            connectedStones++;
                            burialChamber[i + 1][j].setCounted();
                        } else if (i > 0 && burialChamber[i - 1][j].getColor().equals(c) && !burialChamber[i][j].isCounted()) {
                            connectedStones++;
                            burialChamber[i - 1][j].setCounted();

                        }else if (j > 0 && burialChamber[i][j - 1].getColor().equals(c) && !burialChamber[i][j - 1].isCounted()) {
                            connectedStones++;//tutti
                            burialChamber[i][j - 1].setCounted();
                        }else if( !(j < 7 && burialChamber[i][j + 1].getColor().equals(c)) && !(i < 2 && burialChamber[i + 1][j].getColor().equals(c))
                                && !(i > 0 && burialChamber[i - 1][j].getColor().equals(c)) && !(j > 0 && burialChamber[i][j - 1].getColor().equals(c))){
                            oneStoneField++;
                        }
                    }
                }
            }
            if(connectedStones == 2){
                pointsOfBurialChamber.put(c,3+oneStoneField);
            }else if(connectedStones == 3){
                pointsOfBurialChamber.put(c,6+oneStoneField);
            }else if(connectedStones == 4){
                pointsOfBurialChamber.put(c,10+oneStoneField);
            }else if(connectedStones == 5){
                pointsOfBurialChamber.put(c,15+oneStoneField);
            }else if(connectedStones>5){
                pointsOfBurialChamber.put(c,15+2*(connectedStones-5)+oneStoneField);
            }else{
                pointsOfBurialChamber.put(c,oneStoneField);
            }
        }
        return pointsOfBurialChamber;
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
