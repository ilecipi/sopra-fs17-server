package ch.uzh.ifi.seal.soprafs17.model.DTOs;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;

/**
 * Created by ilecipi on 01.04.17.
 */
public class ShipDTO {
    public Long id;
    public Stone[] stones;
    public boolean isReady;
    public int addedStones = 0;
    public boolean docked;
    public String siteBoard;
    ShipDTO() {
    }
    public ShipDTO(Long id, Stone[] stones, boolean isReady, int addedStones, boolean docked, String siteBoard) {
        this.id = id;
        this.stones = stones;
        this.isReady = isReady;
        this.addedStones = addedStones;
        this.docked = docked;
        this.siteBoard = siteBoard;
    }
}
