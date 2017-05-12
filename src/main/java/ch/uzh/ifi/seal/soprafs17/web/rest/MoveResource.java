package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.MoveDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.MCDecoration;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.Statue;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.*;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Market;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.RoundService;
import ch.uzh.ifi.seal.soprafs17.service.ruleEngine.RuleBook;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import ch.uzh.ifi.seal.soprafs17.service.MoveService;
import ch.uzh.ifi.seal.soprafs17.service.SiteBoardsService;
import ch.uzh.ifi.seal.soprafs17.service.validatorEngine.ValidatorManager;
import ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erion on 29.03.17.
 */
@RestController
public class MoveResource extends GenericResource {

    static final String CONTEXT = "/games";

    @Autowired
    MoveService moveService;

    @Autowired
    GameService gameService;

    /**
     *
     * @param moveId
     * @return a move
     */
    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/moves/{moveId}")
    @ResponseStatus(HttpStatus.OK)
    public MoveDTO getMove(@PathVariable Long moveId) {
        AMove m = moveService.getMove(moveId);
        return new MoveDTO(m.getId(), m.getUser().getId(), m.getRound().getId(), m.getGame().getId());
    }

    /**
     *
     * @param gameId
     * @param roundId
     * @param shipId
     * @param playerToken
     * @param position
     */
    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/ships/{shipId}", method = RequestMethod.POST)
    public synchronized void addStoneToShip(@PathVariable Long gameId, @PathVariable Long roundId, @PathVariable Long shipId, @RequestParam("playerToken") String playerToken, @RequestParam("position") int position) {
        moveService.addStoneToShip(gameId,playerToken,shipId,roundId,position);
    }

    /**
     *
     * @param gameId
     * @param roundId
     * @param shipId
     * @param playerToken
     * @param siteBoardsType
     */
    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/ships/{shipId}", method = RequestMethod.PUT)
    public synchronized void sailShip(@PathVariable Long gameId, @PathVariable Long roundId, @PathVariable Long shipId, @RequestParam("playerToken") String playerToken,
                           @RequestParam("siteBoardsType") String siteBoardsType) {
        moveService.sailShip(gameId,roundId,shipId,playerToken,siteBoardsType);
        gameService.updateCounter(gameId);

    }

    /**
     *
     * @param gameId
     * @param roundId
     * @param playerToken
     */
    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/users", method = RequestMethod.POST)
    public synchronized void getStones(@PathVariable Long gameId,@PathVariable Long roundId,@RequestParam("playerToken") String playerToken){
            moveService.getStone(gameId,roundId,playerToken);
    }

    /**
     *
     * @param gameId
     * @param roundId
     * @param playerToken
     * @param position
     */
    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/market", method = RequestMethod.POST)
    public synchronized void giveCardToUser(@PathVariable Long gameId,@PathVariable Long roundId,@RequestParam("playerToken") String playerToken, @RequestParam("position") int position){
            moveService.giveCardToUser(gameId,roundId,playerToken,position);
    }

    /**
     *
     * @param gameId
     * @param roundId
     * @param playerToken
     * @param marketCardId
     */
    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/marketcard", method = RequestMethod.PUT)
    public synchronized void playMarketCard(@PathVariable Long gameId,@PathVariable Long roundId,@RequestParam("playerToken") String playerToken,
    @RequestParam("marketCardId") Long marketCardId) {
            moveService.playMarketCard(gameId,roundId,playerToken,marketCardId);
    }

    /**
     *
     * @param gameId
     * @param roundId
     * @param playerToken
     * @param userColors
     */
    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/lever", method = RequestMethod.PUT)
    public synchronized void playLeverCard(@PathVariable Long gameId,@PathVariable Long roundId,@RequestParam("playerToken") String playerToken,
                                 @RequestParam("userColors") List<String> userColors) {
            moveService.playLeverCard(gameId,roundId,playerToken,userColors);
    }
}

