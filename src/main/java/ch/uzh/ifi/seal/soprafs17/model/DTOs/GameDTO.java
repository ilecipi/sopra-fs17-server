package ch.uzh.ifi.seal.soprafs17.model.DTOs;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;

import java.util.List;

/**
 * Created by ilecipi on 01.04.17.
 */
public class GameDTO {
    GameDTO(){}
    public GameDTO(Long id, String name, String owner, GameStatus status, Long currentPlayer, Long nextPlayer,
                   List<Long> rounds, List<Long> players, List<Long> siteBoards) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.status = status;
        this.currentPlayer = currentPlayer;
        this.nextPlayer = nextPlayer;
        this.rounds = rounds;
        this.players = players;
        this.siteBoards = siteBoards;
    }

    public Long id;
    public String name;
    public String owner;
    public GameStatus status;
    public Long currentPlayer;
    public Long nextPlayer;
    public List<Long> rounds;
    public List<Long> players;
    public List<Long> siteBoards;
}
