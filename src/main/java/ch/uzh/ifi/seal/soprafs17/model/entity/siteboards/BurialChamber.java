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
@DiscriminatorValue("burialchamber")
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
//        TESTING PURPOSES
//        //1
//        firstRow.add(new Stone("brown"));
//        secondRow.add(new Stone("grey"));
//        thirdRow.add(new Stone("white"));
//        //2
//        firstRow.add(new Stone("grey"));
//        secondRow.add(new Stone("brown"));
//        thirdRow.add(new Stone("white"));
//        //3
//        firstRow.add(new Stone("black"));
//        secondRow.add(new Stone("black"));
//        thirdRow.add(new Stone("black"));
//        //4
//        firstRow.add(new Stone("black"));
//        secondRow.add(new Stone("black"));
//        thirdRow.add(new Stone("black"));
//        //5
//        firstRow.add(new Stone("black"));
//        secondRow.add(new Stone("black"));
//        thirdRow.add(new Stone("black"));
//        //6
//        firstRow.add(new Stone("white"));
//        secondRow.add(new Stone("white"));
//        thirdRow.add(new Stone("white"));
//        //7
//        firstRow.add(new Stone("white"));
//        secondRow.add(new Stone("white"));
//        thirdRow.add(new Stone("white"));
//        //8
//        firstRow.add(new Stone("brown"));
//        secondRow.add(new Stone("white"));
//        thirdRow.add(new Stone("brown"));
        Stone[][] burialChamber = new Stone[3][8];
        burialChamber = initializeBurialChamberArray(burialChamber);
        for (String c : colors) {
            int connectedStones = 0;
            int oneStoneField=0;
            for (int j = 0; j < 8; j++) {
                for (int i = 0; i < 3; i++) {
                    if (burialChamber[i][j] != null && burialChamber[i][j].getColor().equals(c)) {
                        if (j < 6 &&burialChamber[i][j + 1]!= null && burialChamber[i][j + 1].getColor().equals(c) && !burialChamber[i][j + 1].isCounted()) {
                            connectedStones++;
                            burialChamber[i][j + 1].setCounted();

                        }
                        if (i < 1 && burialChamber[i + 1][j]!= null && burialChamber[i + 1][j].getColor().equals(c) && !burialChamber[i + 1][j].isCounted()) {
                            connectedStones++;
                            burialChamber[i + 1][j].setCounted();
                        }
                        if (i > 0 && burialChamber[i - 1][j]!= null &&burialChamber[i - 1][j].getColor().equals(c) && !burialChamber[i-1][j].isCounted()) {
                            connectedStones++;
                            burialChamber[i - 1][j].setCounted();


                        }
                        if (j > 0 && burialChamber[i][j - 1]!= null &&burialChamber[i][j - 1].getColor().equals(c) && !burialChamber[i][j - 1].isCounted()) {
                            connectedStones++;//tutti
                            burialChamber[i][j - 1].setCounted();
                        }
                        if(j < 6 && i > 0 && i < 1 && j > 0
                                &&burialChamber[i][j + 1]!=null &&burialChamber[i + 1][j]!=null && burialChamber[i - 1][j]!=null&& burialChamber[i][j - 1]!=null
                                &&!burialChamber[i][j + 1].getColor().equals(c)
                                &&!burialChamber[i + 1][j].getColor().equals(c)
                                &&!burialChamber[i - 1][j].getColor().equals(c)
                                &&!burialChamber[i][j - 1].getColor().equals(c)){
                                    burialChamber[i][j].setCounted();
                                    oneStoneField++;
                        }
                        //Single stone in the middle somewhere
                        if(i>0&&i<2&&j>0&&j<7
                                &&((burialChamber[i-1][j]!=null&&!burialChamber[i-1][j].getColor().equals(c))||burialChamber[i-1][j]==null)
                                &&((burialChamber[i+1][j]!=null&&!burialChamber[i+1][j].getColor().equals(c))||burialChamber[i+1][j]==null)
                                &&((burialChamber[i][j-1]!=null&&!burialChamber[i][j-1].getColor().equals(c))||burialChamber[i][j-1]==null)
                                &&((burialChamber[i][j+1]!=null&&!burialChamber[i][j+1].getColor().equals(c))||burialChamber[i][j+1]==null)){
                                burialChamber[i][j].setCounted();
                                oneStoneField++;
                        }else {
                            //SPECIAL CASES:
                            //Stone on the left border
                            if (i > 0 && i < 2 && j == 0
                                    && ((burialChamber[i - 1][j] != null && !burialChamber[i - 1][j].getColor().equals(c)) || burialChamber[i - 1][j] == null)
                                    && ((burialChamber[i + 1][j] != null && !burialChamber[i + 1][j].getColor().equals(c)) || burialChamber[i + 1][j] == null)
                                    && ((burialChamber[i][j + 1] != null && !burialChamber[i][j + 1].getColor().equals(c)) || burialChamber[i][j + 1] == null)) {
                                burialChamber[i][j].setCounted();
                                oneStoneField++;
                            }
                            //Stone on the right border
                            if (i > 0 && i < 2 && j == 7
                                    && ((burialChamber[i - 1][j] != null && !burialChamber[i - 1][j].getColor().equals(c)) || burialChamber[i - 1][j] == null)
                                    && ((burialChamber[i + 1][j] != null && !burialChamber[i + 1][j].getColor().equals(c)) || burialChamber[i + 1][j] == null)
                                    && ((burialChamber[i][j - 1] != null && !burialChamber[i][j - 1].getColor().equals(c)) || burialChamber[i][j - 1] == null)) {
                                burialChamber[i][j].setCounted();
                                oneStoneField++;
                            }
                            //Stone on the upper border
                            if (i == 0 && j > 0 && j < 7
                                    && ((burialChamber[i][j - 1] != null && !burialChamber[i][j - 1].getColor().equals(c)) || burialChamber[i][j - 1] == null)
                                    && ((burialChamber[i + 1][j] != null && !burialChamber[i + 1][j].getColor().equals(c)) || burialChamber[i + 1][j] == null)
                                    && ((burialChamber[i][j + 1] != null && !burialChamber[i][j + 1].getColor().equals(c)) || burialChamber[i][j + 1] == null)) {
                                burialChamber[i][j].setCounted();
                                oneStoneField++;
                            }
                            //Stone on the lower border
                            if (i == 2 && j > 0 && j < 7
                                    && ((burialChamber[i][j - 1] != null && !burialChamber[i][j - 1].getColor().equals(c)) || burialChamber[i][j - 1] == null)
                                    && ((burialChamber[i - 1][j] != null && !burialChamber[i - 1][j].getColor().equals(c)) || burialChamber[i - 1][j] == null)
                                    && ((burialChamber[i][j + 1] != null && !burialChamber[i][j + 1].getColor().equals(c)) || burialChamber[i][j + 1] == null)) {
                                burialChamber[i][j].setCounted();
                                oneStoneField++;
                            }
                            //Left Upper corner
                            if ((i == 0 && j == 0 && burialChamber[i][j] != null)
                                    && ((burialChamber[i][j + 1] != null && !burialChamber[i][j + 1].getColor().equals(c)) || burialChamber[i][j + 1] == null)
                                    && ((burialChamber[i + 1][j] != null && !burialChamber[i + 1][j].getColor().equals(c)) || burialChamber[i + 1][j] == null)) {
                                burialChamber[i][j].setCounted();
                                oneStoneField++;
                            }
                            //Left Bottom corner
                            if ((i == 2 && j == 0 && burialChamber[i][j] != null)
                                    && ((burialChamber[i][j + 1] != null && !burialChamber[i][j + 1].getColor().equals(c)) || burialChamber[i][j + 1] == null)
                                    && ((burialChamber[i - 1][j] != null && !burialChamber[i - 1][j].getColor().equals(c)) || burialChamber[i - 1][j] == null)) {
                                burialChamber[i][j].setCounted();
                                oneStoneField++;
                            }
                            //Right Upper corner
                            if ((i == 0 && j == 7 && burialChamber[i][j] != null)
                                    && ((burialChamber[i][j - 1] != null && !burialChamber[i][j - 1].getColor().equals(c)) || burialChamber[i][j - 1] == null)
                                    && ((burialChamber[i + 1][j] != null && !burialChamber[i + 1][j].getColor().equals(c)) || burialChamber[i + 1][j] == null)) {
                                burialChamber[i][j].setCounted();
                                oneStoneField++;
                            }
                            //Right Bottom corner
                            if ((i == 2 && j == 7 && burialChamber[i][j] != null)
                                    && ((burialChamber[i][j - 1] != null && !burialChamber[i][j - 1].getColor().equals(c)) || burialChamber[i][j - 1] == null)
                                    && ((burialChamber[i - 1][j] != null && !burialChamber[i - 1][j].getColor().equals(c)) || burialChamber[i - 1][j] == null)) {
                                burialChamber[i][j].setCounted();
                                oneStoneField++;
                            }
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
//        PRINT THE ARRAY ON THE CONSOLE FOR TESTING PURPOSES
//        for(int i=0;i<3;i++){
//            for (int j=0;j<7;j++){
//                if (burialChamber[i][j]!=null) {
//                    System.out.print(burialChamber[i][j].getColor() + "\t");
//                }
//                else{
//                    System.out.println("\t");
//                }
//            }
//            System.out.println("");
//        }
        return pointsOfBurialChamber;
    }

    private Stone[][] initializeBurialChamberArray(Stone[][] burialChamber) {
        for (int i = 0; i < 8; i++) {
            if (firstRow.size()>i&&firstRow.get(i) != null) {
                burialChamber[0][i] = firstRow.get(i);
            }
            if (secondRow.size()>i&&secondRow.get(i) != null) {
                burialChamber[1][i] = secondRow.get(i);
            }
            if (thirdRow.size()>i&&thirdRow.get(i) != null) {
                burialChamber[2][i] = thirdRow.get(i);
            }
        }
        return burialChamber;
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
