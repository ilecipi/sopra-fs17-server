package ch.uzh.ifi.seal.soprafs17.model.entity;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import static ch.uzh.ifi.seal.soprafs17.service.RoundService.MAX_ROUNDS_POSSIBLE;

@Entity
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String owner;

    @Column
    private GameStatus status;

    @OneToOne
    private User currentPlayer;

    @OneToOne
    private User nextPlayer;

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    @OneToMany
    @JsonIgnore
    private List<Round> rounds = new ArrayList<Round>();

    //Colors that are not chosen yet
    @ElementCollection
    private Map<String, Boolean> colors = new HashMap<String, Boolean>() {{
        put("black", false);
        put("white", false);
        put("brown", false);
        put("grey", false);
    }};

    public Map<String, Integer> getPoints() {
        return points;
    }

    public void setPoints(Map<String, Integer> points) {
        this.points = points;
    }

    @ElementCollection
    private Map<String, Integer> points = new HashMap<String, Integer>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "games")
    private List<User> players = new ArrayList<>();

    @OneToMany
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
    private List<SiteBoard> siteBoards;


    public Map<Integer, Integer[]> getShipsCards() {
        return shipsCards;
    }

    @JsonIgnore
    @ElementCollection
    private Map<Integer, Integer[]> shipsCards = new HashMap<>();

    public Map<Integer, String> getMarketCards() {
        return marketCards;
    }

    public void setMarketCards(Map<Integer, String> marketCards) {
        this.marketCards = marketCards;
    }

    @ElementCollection
    private Map<Integer, String> marketCards = new HashMap<>();


    public List<SiteBoard> getSiteBoards() {
        return siteBoards;
    }

    public void setSiteBoards(List<SiteBoard> siteBoards) {
        this.siteBoards = siteBoards;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<User> getPlayers() {
        return players;
    }

    public void setPlayers(List<User> players) {
        this.players = players;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public User getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(User currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setNextPlayer(User nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public User getNextPlayer() {
        return this.nextPlayer;

    }

    public User findNextPlayer() {
        int indexOfCurrentPlayer = getPlayers().indexOf(getCurrentPlayer());
        int indexOfNextPlayer = (indexOfCurrentPlayer + 1) % getPlayers().size();
        setCurrentPlayer(getPlayers().get(indexOfNextPlayer));
        setNextPlayer(getPlayers().get(indexOfNextPlayer % getPlayers().size()));
        int index = (this.getPlayers().lastIndexOf(this.getCurrentPlayer()) + 1) % this.getPlayers().size();
        this.setNextPlayer(this.getPlayers().get(index));

        return currentPlayer;
    }

    public Map<String, Boolean> getColors() {
        return colors;
    }

    public void setColors(Map<String, Boolean> colors) {
        this.colors = colors;
    }

    public void initShipsCards() {
        Random rn = new Random();
        int cardToDelete = rn.nextInt() % 7;
        if (players.size() == 4) {
            this.shipsCards = new Hashtable<Integer, Integer[]>() {{
                put(0, new Integer[]{4, 4, 2, 1});
                put(1, new Integer[]{4, 3, 3, 2});
                put(2, new Integer[]{4, 3, 3, 3});
                put(3, new Integer[]{3, 3, 3, 2});
                put(4, new Integer[]{4, 4, 3, 2});
                put(5, new Integer[]{4, 4, 2, 2});
                put(6, new Integer[]{4, 3, 2, 2});
            }};
            this.shipsCards.remove(cardToDelete);
        } else if (players.size() == 3) {
            this.shipsCards = new Hashtable<Integer, Integer[]>() {{
                put(0, new Integer[]{3, 3, 2, 2});
                put(1, new Integer[]{3, 3, 3, 2});
                put(2, new Integer[]{4, 3, 2, 1});
                put(3, new Integer[]{4, 3, 2, 2});
                put(4, new Integer[]{4, 4, 2, 1});
                put(5, new Integer[]{4, 2, 2, 1});
                put(6, new Integer[]{4, 2, 2, 1});
            }};
            this.shipsCards.remove(cardToDelete);
        } else {
            this.shipsCards = new Hashtable<Integer, Integer[]>() {{
                put(0, new Integer[]{4, 3, 2, 2});
                put(1, new Integer[]{4, 3, 3, 1});
                put(2, new Integer[]{4, 2, 2, 1});
                put(3, new Integer[]{3, 2, 2, 1});
                put(4, new Integer[]{3, 3, 2, 2});
                put(5, new Integer[]{3, 3, 2, 1});
                put(6, new Integer[]{4, 3, 2, 1});
            }};
            this.shipsCards.remove(cardToDelete);
        }
    }

    public void initMarketCards() {
        Map<Integer, String> ordered = new HashMap<Integer, String>() {{
            //For testing cards
//            for (int i = 0; i < 140; i++) {
//                put(i , "CHISEL");
//            }
            put(0, "PAVED_PATH");
            put(1, "PAVED_PATH");
            put(2, "SARCOPHAGUS");
            put(3, "SARCOPHAGUS");
            put(4, "ENTRANCE");
            put(5, "ENTRANCE");
            put(6, "PYRAMID_DECORATION");
            put(7, "PYRAMID_DECORATION");
            put(8, "TEMPLE_DECORATION");
            put(9, "TEMPLE_DECORATION");
            put(10, "BURIAL_CHAMBER_DECORATION");
            put(11, "BURIAL_CHAMBER_DECORATION");
            put(12, "OBELISK_DECORATION");
            put(13, "OBELISK_DECORATION");
            for (int i = 0; i < 10; i++) {
                put(i + 14, "STATUE");
            }
            put(24, "SAIL");
            put(25, "CHISEL");
            put(26, "CHISEL");
            put(27, "CHISEL");
            put(28, "LEVER");
            put(29, "LEVER");
            put(30, "HAMMER");
            put(31, "HAMMER");
            put(32, "SAIL");
            put(33, "SAIL");
        }};

        List<Integer> keys = new ArrayList(ordered.keySet());
        Collections.shuffle(keys);
        int counter = 0;
        for (Integer o : keys) {
            // Access keys/values in a random order

            this.marketCards.put(counter++, ordered.get(o));
        }
    }

    @ElementCollection
    Map<String, Integer> tmpPyramidPointsDispari;
    @ElementCollection
    Map<String, Integer> tmpPyramidPointsPari;
    @ElementCollection
    Map<String, Integer> tmpTemplePointsDispari;
    @ElementCollection
    Map<String, Integer> tmpTemplePointsPari;


    public void collectPoints() {
        List<SiteBoard> siteBoards = this.getSiteBoards();
        int currentRound = this.getRounds().size() - 1;
        List<AShip> ships = this.getRounds().get(currentRound).getShips();
        boolean allShipsDocked = true;

        for (AShip ship : ships) {
            if (!ship.isDocked()) {
                allShipsDocked = false;
            }
        }

        Temple temple = null;
        Pyramid pyramid = null;
        BurialChamber burialChamber = null;
        Obelisk obelisk = null;

        for (SiteBoard s : siteBoards) {
            if (s.getDiscriminatorValue().equals("temple")) {
                temple = (Temple) s;
            } else if (s.getDiscriminatorValue().equals("pyramid")) {
                pyramid = (Pyramid) s;
            } else if (s.getDiscriminatorValue().equals("burialchamber")) {
                burialChamber = (BurialChamber) s;
            } else if (s.getDiscriminatorValue().equals("obelisk")) {
                obelisk = (Obelisk) s;
            }
        }

        if (getRounds().size() % 2 == 1) {
            tmpPyramidPointsDispari = pyramid.countAfterMove();
        } else if (getRounds().size() % 2 == 0) {
            tmpPyramidPointsPari = pyramid.countAfterMove();
        }

        if (pyramid.isOccupied() && !pyramid.isCounted()) {
            pyramid.setCounted(true);
            for (String color : tmpPyramidPointsDispari.keySet()) {
                if (this.colors.get(color)) {

                    if (this.getRounds().size() == 1) {
                        this.points.put(color, points.get(color) + tmpPyramidPointsDispari.get(color));
                    } else if (this.getRounds().size() % 2 ==0 && !tmpPyramidPointsDispari.get(color).equals(tmpPyramidPointsPari.get(color))) {
                        this.points.put(color, points.get(color) + (tmpPyramidPointsPari.get(color) - tmpPyramidPointsDispari.get(color)));
                    }else if (this.getRounds().size() % 2 ==1 && !tmpPyramidPointsDispari.get(color).equals(tmpPyramidPointsPari.get(color))) {
                        this.points.put(color, points.get(color) + (tmpPyramidPointsDispari.get(color) - tmpPyramidPointsPari.get(color)));
                    }
                }
            }
        }

        if (allShipsDocked && temple.isOccupied() && !temple.isCounted()) {
            tmpTemplePointsDispari = temple.countEndOfRound();
            temple.setCounted(true);
            for (String color : tmpTemplePointsDispari.keySet()) {
                if (this.colors.get(color)) {
                    this.points.put(color,points.get(color) + tmpTemplePointsDispari.get(color));
                }
            }
        }

        if (this.getRounds().size() == MAX_ROUNDS_POSSIBLE && allShipsDocked) {
            Map<String, Integer> tmpBurialChamberPoints = burialChamber.countEndOfGame();
            Map<String, Integer> tmpObeliskPoints = obelisk.countEndOfGame();
            if (!burialChamber.isCounted() && tmpBurialChamberPoints != null) {
                burialChamber.setCounted(true);
                for (String color : tmpBurialChamberPoints.keySet()) {
                    if (this.colors.get(color)) {
                        this.points.put(color, points.get(color) + tmpBurialChamberPoints.get(color));
                    }
                }
            }
            if (!obelisk.isCounted() && tmpObeliskPoints != null) {
                obelisk.setCounted(true);
                for (String color : tmpObeliskPoints.keySet()) {
                    if (this.colors.get(color)) {
                        this.points.put(color, points.get(color) + tmpObeliskPoints.get(color));
                    }
                }
            }
        }
    }

    @JsonIgnore
    public Market getMarket(){
        if (!this.siteBoards.isEmpty()) {
            for (SiteBoard s : this.siteBoards) {
                if (s.getDiscriminatorValue().equals("market")) {
                    return ((Market) s);
                }
            }
        }
        return null;
    }

    @JsonIgnore
    public BurialChamber getBurialChamber(){
        if (!this.siteBoards.isEmpty()) {
            for (SiteBoard s : this.siteBoards) {
                if (s.getDiscriminatorValue().equals("burialchamber")) {
                    return ((BurialChamber) s);
                }
            }
        }
        return null;
    }

    @JsonIgnore
    public Obelisk getObelisk(){
        if (!this.siteBoards.isEmpty()) {
            for (SiteBoard s : this.siteBoards) {
                if (s.getDiscriminatorValue().equals("obelisk")) {
                    return ((Obelisk) s);
                }
            }
        }
        return null;
    }

    @JsonIgnore
    public Pyramid getPyramid(){
        if (!this.siteBoards.isEmpty()) {
            for (SiteBoard s : this.siteBoards) {
                if (s.getDiscriminatorValue().equals("pyramid")) {
                    return ((Pyramid) s);
                }
            }
        }
        return null;
    }

    @JsonIgnore
    public Temple getTemple(){
        if (!this.siteBoards.isEmpty()) {
            for (SiteBoard s : this.siteBoards) {
                if (s.getDiscriminatorValue().equals("temple")) {
                    return ((Temple) s);
                }
            }
        }
        return null;
    }

    @JsonIgnore
    public Round getCurrentRound(){
        return this.rounds.get(this.rounds.size()-1);
    }
}