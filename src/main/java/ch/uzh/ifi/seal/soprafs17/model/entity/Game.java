package ch.uzh.ifi.seal.soprafs17.model.entity;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.FourSeatedShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.IShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Game implements Serializable {

	/**
	 *
	 */
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

	//Colors that are not chosen yet
	@ElementCollection
	private Map<String, Boolean> colors = new HashMap<String,Boolean>(){{
            put("black", false);
            put("white", false);
            put("brown", false);
            put("grey", false);
        }};


    @OneToMany(mappedBy="game")
    private List<Move> moves;

    @ManyToMany(mappedBy="games")
    private List<User> players;

	@OneToMany
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private List<SiteBoard> siteBoards;

//	@OneToMany
//	private List<AShip> ships;


	public Map<Integer, Integer[]> getShipsCards() {
		return shipsCards;
	}
	@JsonIgnore
	@ElementCollection
	private Map<Integer, Integer[]> shipsCards;

	public List<SiteBoard> getSiteBoards() {
		return siteBoards;
	}

	public void setSiteBoards(List<SiteBoard> siteBoards) {
		this.siteBoards = siteBoards;
	}

//	public List<AShip> getShips() {
//		return ships;
//	}
//
//	public void setShips(List<AShip> ships) {
//		this.ships = ships;
//	}


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

	public List<Move> getMoves() {
		return moves;
	}

	public void setMoves(List<Move> moves) {
		this.moves = moves;
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
	public User getNextPlayer(){
		return this.nextPlayer;

	}

	public User findNextPlayer(){
      int indexOfCurrentPlayer=getPlayers().indexOf(getCurrentPlayer());
		int indexOfNextPlayer=(indexOfCurrentPlayer+1)%getPlayers().size();
		setCurrentPlayer(getPlayers().get(indexOfNextPlayer));
		setNextPlayer(getPlayers().get(indexOfNextPlayer%getPlayers().size()));

		return	currentPlayer;
    }

	public Map<String, Boolean> getColors() {
		return colors;
	}

	public void setColors(Map<String, Boolean> colors) {
		this.colors = colors;
	}

	public void initShipsCards(){
		Random rn = new Random();
		int cardToDelete=rn.nextInt()%7;
		if(players.size()==4){
			this.shipsCards= new Hashtable<Integer, Integer[]>(){{
				put(0,new Integer[]{4,4,2,1});
				put(1,new Integer[]{4,3,3,2});
				put(2,new Integer[]{4,3,3,3});
				put(3,new Integer[]{3,3,3,2});
				put(4,new Integer[]{4,4,3,2});
				put(5,new Integer[]{4,4,2,2});
				put(6,new Integer[]{4,3,2,2});
			}};
			this.shipsCards.remove(cardToDelete);
		}
		else if(players.size()==3){
			this.shipsCards = new Hashtable<Integer, Integer[]>(){{
				put(0,new Integer[]{3,3,2,2});
				put(1,new Integer[]{3,3,3,2});
				put(2,new Integer[]{4,3,2,1});
				put(3,new Integer[]{4,3,2,2});
				put(4,new Integer[]{4,4,2,1});
				put(5,new Integer[]{4,2,2,1});
				put(6,new Integer[]{4,2,2,1});
			}};
			this.shipsCards.remove(cardToDelete);
		}
		else{
			this.shipsCards = new Hashtable<Integer, Integer[]>(){{
				put(0,new Integer[]{4,3,2,2});
				put(1,new Integer[]{4,3,3,1});
				put(2,new Integer[]{4,2,2,1});
				put(3,new Integer[]{3,2,2,1});
				put(4,new Integer[]{3,3,2,2});
				put(5,new Integer[]{3,3,2,1});
				put(6,new Integer[]{4,3,2,1});
			}};
			this.shipsCards.remove(cardToDelete);
		}
	}

}
