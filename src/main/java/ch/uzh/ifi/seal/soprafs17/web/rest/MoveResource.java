package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.MoveDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AddStoneToShipMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.RuleBook;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import ch.uzh.ifi.seal.soprafs17.service.MoveService;
import ch.uzh.ifi.seal.soprafs17.service.ShipService;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.ValidatorManager;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by erion on 29.03.17.
 */
@RestController
public class MoveResource extends GenericResource {

    @Autowired
    ShipService shipService;
    @Autowired
    SiteBoardRepository siteBoardsService;
    @Autowired
    MoveService moveService;
    @Autowired
    GameRepository gameRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    ShipRepository shipRepo;
    @Autowired
    SiteBoardRepository siteBoardRepo;
    @Autowired
    RoundRepository roundRepo;
    @Autowired
    MoveRepository moveRepo;
    @Autowired
    RuleBook ruleBook;
    @Autowired
    private ValidatorManager validatorManager;


    static final String CONTEXT = "/games";

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/moves/{moveId}")
    @ResponseStatus(HttpStatus.OK)
    public MoveDTO getMove(@PathVariable Long moveId) {
        AMove m = moveRepo.findOne(moveId);
        logger.debug("getMove: " + moveId);
        System.out.println(m.getId());
        return new MoveDTO(m.getId(), m.getUser().getId(), m.getRound().getId(), m.getGame().getId());
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/ships/{shipId}", method = RequestMethod.POST)

    public String addStoneToShip(HttpServletResponse response, @PathVariable Long gameId, @PathVariable Long roundId, @PathVariable Long shipId, @RequestParam("playerToken") String playerToken, @RequestParam("position") int position) {
        Game game = gameRepo.findOne(gameId);
        User user = userRepo.findByToken(playerToken);
        AShip ship = shipRepo.findById(shipId);
        Round round = roundRepo.findById(roundId);
        if (game != null && user != null && ship != null && round != null) {
            AddStoneToShipMove move = moveRepo.save(new AddStoneToShipMove(game, user, ship, position, round));
            try {
                validatorManager.validateSync(game, move);
            } catch (ValidationException e) {
                validationExceptionHandler(e, response);
                return e.getMessage();
            }
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            moveService.addStoneToShip(game, move);

            gameRepo.save(game);
            userRepo.save(user);
            shipRepo.save(ship);
            roundRepo.save(round);
            return "OK";
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "NullException";
        }
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/ships/{shipId}/siteboards/{siteBoardId}", method = RequestMethod.POST)
    public String sailShip(HttpServletResponse response,@PathVariable Long gameId, @PathVariable Long roundId, @PathVariable Long siteBoardId, @PathVariable Long shipId, @RequestParam("playerToken") String playerToken) {
        Game game = gameRepo.findOne(gameId);
        User user = userRepo.findByToken(playerToken);
        AShip ship = shipRepo.findById(shipId);
        SiteBoard siteBoard = siteBoardRepo.findById(siteBoardId);
        Round round = roundRepo.findById(roundId);
        if (game != null && user != null && ship != null && siteBoard != null && round != null) {
            SailShipMove move = moveRepo.save(new SailShipMove(game, user, ship, round, siteBoard));
            try {
                validatorManager.validateSync(game, move);
            } catch (ValidationException e) {
                validationExceptionHandler(e,response);
                return e.getMessage();
            }

            moveService.sailShip(game, move);
            moveService.addStoneToSiteBoard(siteBoardId, playerToken, gameId, shipId);
            siteBoardRepo.save(siteBoard);
            gameRepo.save(game);
            userRepo.save(user);
            shipRepo.save(ship);
            roundRepo.save(round);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return "OK";
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "NullException";
        }
    }

}
