package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.MoveDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AddStoneToShipMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.RuleBook;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import ch.uzh.ifi.seal.soprafs17.service.MoveService;
import ch.uzh.ifi.seal.soprafs17.service.ShipService;
import ch.uzh.ifi.seal.soprafs17.service.SiteBoardsService;
import ch.uzh.ifi.seal.soprafs17.service.TempleService;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.ValidatorManager;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception.NotCurrentPlayerException;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;

/**
 * Created by erion on 29.03.17.
 */
@RestController
public class MoveResource extends GenericResource {

    @Autowired
    ShipService shipService;
    @Autowired
    TempleService templeService;
    @Autowired
    SiteBoardsService siteBoardsService;
    @Autowired
    MoveService moveService;
    @Autowired
    GameRepository gameRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    ShipRepository shipRepo;
    @Autowired
    RoundRepository roundRepo;
    @Autowired
    MoveRepository moveRepo;
    @Autowired
    RuleBook ruleBook;
    @Autowired
    private ValidatorManager validatorManager;


    static final String CONTEXT = "/games";

    @RequestMapping(value = CONTEXT + "{gameId}/move")
    @ResponseStatus(HttpStatus.OK)
    public MoveDTO getMove(@PathVariable Long moveId) {
        logger.debug("getMove: " + moveId);
        AMove m = moveService.getMove(moveId);
        return new MoveDTO(m.getId(),m.getUser().getId(),m.getRound().getId(),m.getGame().getId());
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/ships/{shipId}", method = RequestMethod.POST)

    public String addStoneToShip(HttpServletResponse response, @PathVariable Long gameId, @PathVariable Long roundId, @PathVariable Long shipId, @RequestParam("playerToken") String playerToken, @RequestParam("position") int position) {
        Game game = gameRepo.findOne(gameId);
        User user = userRepo.findByToken(playerToken);
        AShip ship = shipRepo.findById(shipId);
        Round round = roundRepo.findById(roundId);
        AddStoneToShipMove move = moveRepo.save(new AddStoneToShipMove(game,user,ship,position,round));
        try {
            validatorManager.validateSync(game,move);
        }
        catch(ValidationException e){
                validationExceptionHandler(e, response);
                 return e.getMessage();
        }
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        moveService.addStoneToShip(gameId,roundId,shipId,playerToken,position);
        return "OK";
    }

//    @RequestMapping(value = CONTEXT + "/{gameId}/{templeId}", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    public void addStoneToTemple(@PathVariable Long gameId,@PathVariable Long templeId,@RequestParam("playerToken") String playerToken) {
//        moveService.addStoneToTemple(templeId,playerToken,gameId);
//    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/ships/{shipId}/siteboards/{siteBoardId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void sailShip(@PathVariable Long gameId, @PathVariable Long roundId,@PathVariable Long siteBoardId,@PathVariable Long shipId, @RequestParam("playerToken") String playerToken) {
        moveService.sailShip(gameId,roundId,shipId,playerToken,siteBoardId);
        moveService.addStoneToTemple(siteBoardId,playerToken,gameId,shipId);
    }



}
