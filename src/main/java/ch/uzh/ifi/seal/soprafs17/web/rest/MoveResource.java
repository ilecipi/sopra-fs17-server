package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.MoveDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.service.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by erion on 29.03.17.
 */
@RestController
public class MoveResource extends GenericResource {

    static final String CONTEXT = "/games";


    @Autowired
    MoveService moveService;

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/moves/{moveId}")
    @ResponseStatus(HttpStatus.OK)
    public MoveDTO getMove(@PathVariable Long moveId) {
        AMove m = moveService.getMove(moveId);
        return new MoveDTO(m.getId(), m.getUser().getId(), m.getRound().getId(), m.getGame().getId());
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/ships/{shipId}", method = RequestMethod.POST)
    public synchronized void addStoneToShip(@PathVariable Long gameId, @PathVariable Long roundId, @PathVariable Long shipId, @RequestParam("playerToken") String playerToken, @RequestParam("position") int position) {
        moveService.addStoneToShip(gameId, playerToken, shipId, roundId, position);
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/ships/{shipId}", method = RequestMethod.PUT)
    public synchronized void sailShip(@PathVariable Long gameId, @PathVariable Long roundId, @PathVariable Long shipId, @RequestParam("playerToken") String playerToken,
                                      @RequestParam("siteBoardsType") String siteBoardsType) {
        moveService.sailShip(gameId, roundId, shipId, playerToken, siteBoardsType);
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/users", method = RequestMethod.POST)
    public void getStones(@PathVariable Long gameId, @PathVariable Long roundId, @RequestParam("playerToken") String playerToken) {
        moveService.getStone(gameId, roundId, playerToken);
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/market", method = RequestMethod.POST)
    public void giveCardToUser(@PathVariable Long gameId, @PathVariable Long roundId, @RequestParam("playerToken") String playerToken, @RequestParam("position") int position) {
        moveService.giveCardToUser(gameId, roundId, playerToken, position);
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/marketcard", method = RequestMethod.PUT)
    public synchronized void playMarketCard(@PathVariable Long gameId, @PathVariable Long roundId, @RequestParam("playerToken") String playerToken,
                                            @RequestParam("marketCardId") Long marketCardId) {
        moveService.playMarketCard(gameId, roundId, playerToken, marketCardId);
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/lever", method = RequestMethod.PUT)
    public synchronized void playLeverCard(@PathVariable Long gameId, @PathVariable Long roundId, @RequestParam("playerToken") String playerToken,
                                           @RequestParam("userColors") List<String> userColors) {
        moveService.playLeverCard(gameId, roundId, playerToken, userColors);
    }
}

