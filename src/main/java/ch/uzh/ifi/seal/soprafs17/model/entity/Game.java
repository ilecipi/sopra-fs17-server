package ch.uzh.ifi.seal.soprafs17.model.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;

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

	public void setNextPlayer(User nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

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

	public User getNextPlayer(){
		return this.nextPlayer;

	}

	public User findNextPlayer(){
        int indexOfCurrentPlayer=getPlayers().indexOf(getCurrentPlayer());
		int indexOfNextPlayer=(indexOfCurrentPlayer+1)%getPlayers().size();
		setCurrentPlayer(getPlayers().get(indexOfNextPlayer));
		return	getCurrentPlayer();
    }

	public Map<String, Boolean> getColors() {
		return colors;
	}

	public void setColors(Map<String, Boolean> colors) {
		this.colors = colors;
	}

}