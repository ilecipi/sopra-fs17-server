package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

/**
 * Created by erion on 20.03.17.
 */
@Entity
@DiscriminatorValue("obelisk")
public class Obelisk extends StoneBoard {

    public Obelisk(){
    }

    public Obelisk(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
        if(this.numberOfPlayers==4){
            this.maxPoints=15;
        }else if(this.numberOfPlayers==3){
            this.maxPoints=12;
        }else{
            this.maxPoints=10;
        }
    }

    @Transient
    public String getDiscriminatorValue() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    private int numberOfPlayers;

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @OneToOne
    private Game game;

    private String[] firstPlayer = null;
    private String[] secondPlayer = null;
    private String[] thirdPlayer = null;
    private String[] fourthPlayer = null;

    private int maxPoints;

    private final String Type = "endOfGame";

    @ElementCollection
    private Map<String,Integer> obelisks = new HashMap<String,Integer>(){{
        put("black",0);
        put("white",0);
        put("brown",0);
        put("grey",0);
    }};


    public void addStone(Stone stone) {
        if(stone!=null){
            if(stone.getColor().equals("brown")) {
                int tmpPoints = obelisks.get(stone.getColor());
                obelisks.put(stone.getColor(), ++tmpPoints);
            }else if(stone.getColor().equals("grey")){
                int tmpPoints = obelisks.get(stone.getColor());
                obelisks.put(stone.getColor(), ++tmpPoints);
            }else if (stone.getColor().equals("black")){
                int tmpPoints = obelisks.get(stone.getColor());
                obelisks.put(stone.getColor(), ++tmpPoints);
            }else{
                int tmpPoints = obelisks.get(stone.getColor());
                obelisks.put(stone.getColor(), ++tmpPoints);
            }
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
        LinkedHashMap<String, Integer> sortedMap = sortHashMap();
        sortedMap = removeNotPresentPlayers(sortedMap);
        Map<String, Integer> points = new HashMap<>();
        initializePlayersArrays(sortedMap);


        if (numberOfPlayers == 2) {
            if (differentPoints(sortedMap)) {
                sortedMap.entrySet().stream().forEach(x -> points.put(x.getKey(), getPoints(x.getValue())));

                return points;
            } else {
                //Two players on the Obelisk
                if (firstPlayer != null && secondPlayer != null && thirdPlayer == null && fourthPlayer == null) {
                    if (firstPlayer[1].equals(secondPlayer[1])) {
                        int tmpPoints = ((10 + 1) / 2);
                        points.put(firstPlayer[0], tmpPoints);
                        points.put(secondPlayer[0], tmpPoints);
                    }
                }
            }
        }
        else if (numberOfPlayers == 3) {
                if (differentPoints(sortedMap)) {
                    sortedMap.entrySet().stream().forEach(x -> points.put(x.getKey(), getPoints(x.getValue())));
                    return points;
                } else {
                    //Three players on the Obelisk
                    if (firstPlayer != null && secondPlayer != null && thirdPlayer != null && fourthPlayer == null) {
                        //case 1: everybody has the same amount of stones on it
                        if (firstPlayer[1].equals(secondPlayer[1]) && secondPlayer[1].equals(thirdPlayer[1])) {
                            int tmpPoints = ((12 + 6 + 1) / 3);
                            points.put(firstPlayer[0], tmpPoints);
                            points.put(secondPlayer[0], tmpPoints);
                            points.put(thirdPlayer[0], tmpPoints);
                        }
                        //case 2: first&second player same number, third different
                        if (firstPlayer[1].equals(secondPlayer[1]) && !secondPlayer[1].equals(thirdPlayer[1])) {
                            int tmpPoints = ((12 + 6) / 2);
                            points.put(firstPlayer[0], tmpPoints);
                            points.put(secondPlayer[0], tmpPoints);
                            points.put(thirdPlayer[0], 1);
                        }
                        //case 3: first different, second&third same amount
                        if (!firstPlayer[1].equals(secondPlayer[1]) && secondPlayer[1].equals(thirdPlayer[1])) {
                            int tmpPoints = ((6 + 1) / 2);
                            points.put(firstPlayer[0], 12);
                            points.put(secondPlayer[0], tmpPoints);
                            points.put(thirdPlayer[0], tmpPoints);
                        }
                    }
                    //Two players on the Obelisk
                    if (firstPlayer != null && secondPlayer != null && thirdPlayer == null && fourthPlayer == null) {
                        //case 4: first&second player same amount
                        if (firstPlayer[1].equals(secondPlayer[1])) {
                            int tmpPoints = ((12 + 6) / 2);
                            points.put(firstPlayer[0], tmpPoints);
                            points.put(secondPlayer[0], tmpPoints);
                        }
                    }
                }
            } else {
                //Four players on the Obelisk
                //Everybody has a different amount of stone on the obelisk
                if (differentPoints(sortedMap)) {
                    sortedMap.entrySet().stream().forEach(x -> points.put(x.getKey(), getPoints(x.getValue())));
                    return points;
                } else {
                    //Four players on the Obelisk
                    if (firstPlayer != null && secondPlayer != null && thirdPlayer != null && fourthPlayer != null) {
                        //case 1: everybody has the same amount of stones on it
                        if (firstPlayer[1].equals(secondPlayer[1]) && secondPlayer[1].equals(thirdPlayer[1])
                                && thirdPlayer[1].equals(fourthPlayer[1])) {
                            int tmpPoints = ((15 + 10 + 5 + 1) / 4);
                            points.put(firstPlayer[0], tmpPoints);
                            points.put(secondPlayer[0], tmpPoints);
                            points.put(thirdPlayer[0], tmpPoints);
                            points.put(fourthPlayer[0], tmpPoints);
                        }
                        //case 2: all the players have the same amount of stones EXCEPT the last one
                        if (firstPlayer[1].equals(secondPlayer[1]) && secondPlayer[1].equals(thirdPlayer[1])
                                && !thirdPlayer[1].equals(fourthPlayer[1])) {
                            int tmpPoints = ((15 + 10 + 5) / 3);
                            points.put(firstPlayer[0], tmpPoints);
                            points.put(secondPlayer[0], tmpPoints);
                            points.put(thirdPlayer[0], tmpPoints);
                            points.put(fourthPlayer[0], 1);
                        }
                        //case 3: first and second player the same result, the last two differents
                        if (firstPlayer[1].equals(secondPlayer[1]) && !secondPlayer[1].equals(thirdPlayer[1])
                                && !thirdPlayer[1].equals(fourthPlayer[1])
                                && !secondPlayer[1].equals(fourthPlayer[1])) {
                            int tmpPoints = ((15 + 10) / 2);
                            points.put(firstPlayer[0], tmpPoints);
                            points.put(secondPlayer[0], tmpPoints);
                            points.put(thirdPlayer[0], 5);
                            points.put(fourthPlayer[0], 1);
                        }
                        //case 4: first two equals, third and fourth equals as well
                        if (firstPlayer[1].equals(secondPlayer[1]) && !secondPlayer[1].equals(thirdPlayer[1])
                                && thirdPlayer[1].equals(fourthPlayer[1])
                                && !secondPlayer[1].equals(fourthPlayer[1])) {
                            int tmpPointsFirstAndSecond = ((15 + 10) / 2);
                            int tmpPointsThirdAndFourth = ((5 + 1) / 2);
                            points.put(firstPlayer[0], tmpPointsFirstAndSecond);
                            points.put(secondPlayer[0], tmpPointsFirstAndSecond);
                            points.put(thirdPlayer[0], tmpPointsThirdAndFourth);
                            points.put(fourthPlayer[0], tmpPointsThirdAndFourth);
                        }
                        //case 5: first different, second&third&fourth equals
                        if (!firstPlayer[1].equals(secondPlayer[1]) && secondPlayer[1].equals(thirdPlayer[1]) &&
                                thirdPlayer[1].equals(fourthPlayer[1])) {
                            int tmpPoints = ((10 + 5 + 1) / 3);
                            points.put(firstPlayer[0], 15);
                            points.put(secondPlayer[0], tmpPoints);
                            points.put(thirdPlayer[0], tmpPoints);
                            points.put(fourthPlayer[0], tmpPoints);
                        }
                        //case 6: first and second different, third and fourth the same
                        if (!firstPlayer[1].equals(secondPlayer[1]) && !secondPlayer[1].equals(thirdPlayer[1]) && thirdPlayer[1].equals(fourthPlayer[1])
                                && !secondPlayer[1].equals(fourthPlayer[1])) {
                            int tmpPoints = ((5 + 1) / 2);
                            points.put(firstPlayer[0], 15);
                            points.put(secondPlayer[0], 10);
                            points.put(thirdPlayer[0], tmpPoints);
                            points.put(fourthPlayer[0], tmpPoints);
                        }
                        //case 7: first  different,second and third same,fourth different
                        if (!firstPlayer[1].equals(secondPlayer[1]) && secondPlayer[1].equals(thirdPlayer[1]) && !thirdPlayer[1].equals(fourthPlayer[1])
                                && !secondPlayer[1].equals(fourthPlayer[1])) {
                            int tmpPoints = ((10 + 5) / 2);
                            points.put(firstPlayer[0], 15);
                            points.put(secondPlayer[0], tmpPoints);
                            points.put(thirdPlayer[0], tmpPoints);
                            points.put(fourthPlayer[0], 1);
                        }
                    }
                    //Three players on the obelisk
                    if (firstPlayer != null && secondPlayer != null && thirdPlayer != null && fourthPlayer == null) {
                        //case 1: everybody has the same amount of stones on it
                        if (firstPlayer[1].equals(secondPlayer[1]) && secondPlayer[1].equals(thirdPlayer[1])) {
                            int tmpPoints = ((15 + 10 + 5) / 3);
                            points.put(firstPlayer[0], tmpPoints);
                            points.put(secondPlayer[0], tmpPoints);
                            points.put(thirdPlayer[0], tmpPoints);
                        }
                        //case 2: first&second the same, third one different
                        if (firstPlayer[1].equals(secondPlayer[1]) && !secondPlayer[1].equals(thirdPlayer[1])) {
                            int tmpPoints = ((15 + 10) / 2);
                            points.put(firstPlayer[0], tmpPoints);
                            points.put(secondPlayer[0], tmpPoints);
                            points.put(thirdPlayer[0], 5);
                        }
                        //case 3: first different, second and third the same
                        if (!firstPlayer[1].equals(secondPlayer[1]) && secondPlayer[1].equals(thirdPlayer[1])) {
                            int tmpPoints = ((10 + 5) / 2);
                            points.put(firstPlayer[0], 15);
                            points.put(secondPlayer[0], tmpPoints);
                            points.put(thirdPlayer[0], tmpPoints);
                        }
                    }
                    //Two players on the obelisk
                    if (firstPlayer != null && secondPlayer != null && thirdPlayer == null && fourthPlayer == null) {
                        if (firstPlayer[1].equals(secondPlayer[1])) {
                            int tmpPoints = ((15 + 10) / 2);
                            points.put(firstPlayer[0], tmpPoints);
                            points.put(secondPlayer[0], tmpPoints);
                        }
                    }
                }
            }
            return points;
        }


    private void initializePlayersArrays(LinkedHashMap<String, Integer> sortedMap) {

        if(sortedMap.keySet().toArray().length>0&&sortedMap.keySet().toArray()[0]!=null) {
            this.firstPlayer = new String[2];
            this.firstPlayer[0]=(String) sortedMap.keySet().toArray()[0];
            this.firstPlayer[1]= sortedMap.values().toArray()[0].toString();
        }
        if(sortedMap.keySet().toArray().length>1&&sortedMap.keySet().toArray()[1]!=null) {
            this.secondPlayer= new String[2];
            this.secondPlayer[0]=(String) sortedMap.keySet().toArray()[1];
            this.secondPlayer[1]=(String) sortedMap.values().toArray()[1].toString();
        }
        if(sortedMap.keySet().toArray().length>2&&sortedMap.keySet().toArray()[2]!=null) {
            this.thirdPlayer = new String[2];
            this.thirdPlayer[0]=(String) sortedMap.keySet().toArray()[2];
            this.thirdPlayer[1]=(String) sortedMap.values().toArray()[2].toString();
        }
        if(sortedMap.keySet().toArray().length>3&&sortedMap.keySet().toArray()[3]!=null) {
            this.fourthPlayer = new String[2];
            this.fourthPlayer[0]=(String) sortedMap.keySet().toArray()[3];
            this.fourthPlayer[1]=(String) sortedMap.values().toArray()[3].toString();
        }
    }

    private LinkedHashMap<String, Integer> removeNotPresentPlayers(LinkedHashMap<String, Integer> sortedMap) {

        for(Iterator<Map.Entry<String,Integer>>it=sortedMap.entrySet().iterator();it.hasNext();){
            Map.Entry<String, Integer> entry = it.next();
            if(entry!=null) {
                if (entry.getValue() == 0) {
                    it.remove();
                }
            }
        }
        return sortedMap;
    }

    private LinkedHashMap<String, Integer> sortHashMap() {
        LinkedHashMap<String,Integer> sortedMap = new LinkedHashMap<>();
        this.obelisks.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }


    private int getPoints(int stone) {
        int tmp = maxPoints;
        if(this.numberOfPlayers==4) {
            if (maxPoints == 0) {
                return 1;
            } else {

                maxPoints = maxPoints - 5;
                return tmp;
            }
        }else if(this.numberOfPlayers==3){
            if (maxPoints == 0) {
                return 1;
            } else {

                maxPoints = maxPoints - 6;
                return tmp;
        }
        }else{
            if (maxPoints == 0) {
                return 1;
            } else {

                maxPoints = maxPoints - 10;
                return tmp;
            }
        }
    }



    private boolean differentPoints(Map<String,Integer> checkMap) {
        for (Map.Entry<String, Integer> s : checkMap.entrySet()) {
            for (Map.Entry<String, Integer> c : checkMap.entrySet()) {
                if (!c.getKey().equals(s.getKey())) {
                    if (c.getValue().equals(s.getValue())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public Map<String, Integer> getObelisks() {
        return obelisks;
    }

    public void setObelisks(Map<String, Integer> obelisks) {
        this.obelisks = obelisks;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;

    }
}
