package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.MoveDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.*;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Market;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;
import ch.uzh.ifi.seal.soprafs17.service.RoundService;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.RuleBook;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import ch.uzh.ifi.seal.soprafs17.service.MoveService;
import ch.uzh.ifi.seal.soprafs17.service.ShipService;
import ch.uzh.ifi.seal.soprafs17.service.SiteBoardsService;
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

    static final String CONTEXT = "/games";
    @Autowired
    RoundService roundService;
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
    ValidatorManager validatorManager;

    @Autowired
    MarketCardRepository marketCardRepository;

    @Autowired
    SiteBoardsService siteBoardsService;

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
            AddStoneToShipMove move =new AddStoneToShipMove(game, user, ship, position, round);
            try {
                validatorManager.validateSync(game, move);
                moveRepo.save(move);
            } catch (ValidationException e) {
                validationExceptionHandler(e,response);
                return e.getMessage();
            }
            moveService.addStoneToShip(game, move);
            gameRepo.save(game);
            userRepo.save(user);
            shipRepo.save(ship);
            roundRepo.save(round);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return "OK";
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "NullException";
        }
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/ships/{shipId}", method = RequestMethod.PUT)
    public String sailShip(HttpServletResponse response,@PathVariable Long gameId, @PathVariable Long roundId, @PathVariable Long shipId, @RequestParam("playerToken") String playerToken,
                           @RequestParam("siteBoardsType") String siteBoardsType) {
        Game game = gameRepo.findOne(gameId);
        User user = userRepo.findByToken(playerToken);
        AShip ship = shipRepo.findById(shipId);
        SiteBoard siteBoard = moveService.findSiteboardsByType(siteBoardsType.toLowerCase(),gameId);
        Round round = roundRepo.findById(roundId);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (game != null && user != null && ship != null && siteBoard != null && round != null) {
            SailShipMove move =new SailShipMove(game, user, ship, round, siteBoard);
            try {
                validatorManager.validateSync(game, move);
                moveRepo.save(move);
            } catch (ValidationException e) {
                validationExceptionHandler(e,response);
                return e.getMessage();
            }
            moveService.sailShip(game, move);
            if(move.getSiteBoard() instanceof StoneBoard) {
                moveService.addStoneToSiteBoard(siteBoard.getId(), playerToken, gameId, shipId);
                game.collectPoints();
                roundService.addRound(game.getId());
            }
            else{
                moveService.addUserToMarket(game,ship);
            }
            siteBoardRepo.save(siteBoard);
            gameRepo.save(game);
            userRepo.save(user);
            shipRepo.save(ship);
            roundRepo.save(round);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return "OK";
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "NullException" + " GAME: "+game+ " USER "+ user + " SHIP: "+ship + " SITEBOARD: "+siteBoard + " ROUND: "+round;
        }
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/users", method = RequestMethod.POST)
    public String getStones(HttpServletResponse response,@PathVariable Long gameId,@PathVariable Long roundId,@RequestParam("playerToken") String playerToken){
        Game game = gameRepo.findOne(gameId);
        User user = userRepo.findByToken(playerToken);
        Round round = roundRepo.findById(roundId);
        if(game != null && user != null && round != null){
            GetStoneMove move =new GetStoneMove(user,round,game);
            try{
                validatorManager.validateSync(game,move);
                moveRepo.save(move);
            } catch (ValidationException e){
                validationExceptionHandler(e,response);
                return e.getMessage();
            }
            moveService.getStone(game,move);
            gameRepo.save(game);
            roundRepo.save(round);
            userRepo.save(user);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return "OK";
        }else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "NullException";
        }
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/market", method = RequestMethod.POST)
    public String giveCardToUser(HttpServletResponse response,@PathVariable Long gameId,@PathVariable Long roundId,@RequestParam("playerToken") String playerToken, @RequestParam("position") int position){
        Game game = gameRepo.findOne(gameId);
        User user = userRepo.findByToken(playerToken);
        Round round = roundRepo.findById(roundId);
        if(game != null && user != null && round != null){
            GiveCardToUserMove move =moveRepo.save(new GiveCardToUserMove(user,round,game,position));
            try{
                validatorManager.validateSync(game,move);
                moveRepo.save(move);
            } catch (ValidationException e){
                validationExceptionHandler(e,response);
                return e.getMessage();
            }
            moveService.giveCardToUser(game,move);
            Market market = siteBoardsService.getMarket(game);
            if(market.getUserColor().isEmpty()&&market.isOccupied()){
                roundService.addRound(game.getId());
            }
            user=userRepo.save(user);
            gameRepo.save(game);
            roundRepo.save(round);
            int counterCard = user.getMarketCards().size()-1;
            marketCardRepository.save(user.getMarketCards().get(counterCard));
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return "OK";
        }else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "NullException";
        }
    }
}
