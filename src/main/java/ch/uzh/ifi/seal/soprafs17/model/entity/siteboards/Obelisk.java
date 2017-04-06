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

    @Transient
    public String getDiscriminatorValue() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

    int numberOfPlayers;

    @Id
    @GeneratedValue
    private Long id;

    private final String Type = "endOfGame";

    @ElementCollection
    private Map<String,Integer> obelisks = new HashMap<String,Integer>(){{
        put("black",0);
        put("white",0);
        put("brown",0);
        put("grey",0);
    }};

    public Obelisk(){
    }

    public Obelisk(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
    }

    public void addStone(Stone stone) {
        if(stone!=null){
            if(stone.getColor().equals("brown")) {
                int tmpPoints = obelisks.get(stone.getColor());
                obelisks.put(stone.getColor(), 40);
            }else if(stone.getColor().equals("grey")){
                int tmpPoints = obelisks.get(stone.getColor());
                obelisks.put(stone.getColor(), 20);
            }else{
                int tmpPoints = obelisks.get(stone.getColor());
                obelisks.put(stone.getColor(), 10+tmpPoints);
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
        if(numberOfPlayers==2){

        }
        else if(numberOfPlayers==3){

        }else{

        }
        return sortedMap;
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
