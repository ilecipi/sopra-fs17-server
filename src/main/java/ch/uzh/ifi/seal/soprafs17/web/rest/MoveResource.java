package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.MoveDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.Lever;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.MCDecoration;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.Statue;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.*;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Market;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;
import ch.uzh.ifi.seal.soprafs17.service.RoundService;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.RuleBook;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import ch.uzh.ifi.seal.soprafs17.service.MoveService;
import ch.uzh.ifi.seal.soprafs17.service.SiteBoardsService;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.ValidatorManager;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception.ValidationException;
import org.hibernate.annotations.SourceType;
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
        return new MoveDTO(m.getId(), m.getUser().getId(), m.getRound().getId(), m.getGame().getId());
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/ships/{shipId}", method = RequestMethod.POST)
    public synchronized String addStoneToShip(HttpServletResponse response, @PathVariable Long gameId, @PathVariable Long roundId, @PathVariable Long shipId, @RequestParam("playerToken") String playerToken, @RequestParam("position") int position) {
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
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "NullException";
        }
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/ships/{shipId}", method = RequestMethod.PUT)
    public synchronized String sailShip(HttpServletResponse response,@PathVariable Long gameId, @PathVariable Long roundId, @PathVariable Long shipId, @RequestParam("playerToken") String playerToken,
                           @RequestParam("siteBoardsType") String siteBoardsType) {
        Game game = gameRepo.findOne(gameId);
        User user = userRepo.findByToken(playerToken);
        AShip ship = shipRepo.findById(shipId);
        SiteBoard siteBoard = moveService.findSiteboardsByType(siteBoardsType.toLowerCase(),gameId);
        Round round = roundRepo.findById(roundId);
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
                    if(!round.isActionCardLever()) {
                        game.collectPoints();
                    }

                //Neither an ImmediateCard nor an ActionCardLever is being played
                if(!round.isImmediateCard()&&!round.isActionCardLever()) {

                    roundService.addRound(game.getId());
                }
            }
            //It's the Market
            else{
                //If the ActionCardLever is not played
                if(!game.getCurrentRound().isActionCardLever()) {
                    moveService.addUserToMarket(game, ship);
                }
                //If the ActionCardLever is played
                else{
                    //array of stones for the client
                    List<String> tmp = new ArrayList<>();
                    for (int i = ship.getStones().length - 1; i >= 0; i--) {
                        if (ship.getStones()[i] != null) {
                            tmp.add(ship.getStones()[i].getColor());
                        }
                    }

                    ship.setDocked(true);
                    ship.setSiteBoard(game.getMarket());
                    //Save the Market Id for later
                    game.setTmpSiteBoardId(game.getMarket().getId());
                    game.getCurrentRound().setListActionCardLever(tmp);
                    roundRepo.save(game.getCurrentRound());
                }
            }
            siteBoardRepo.save(siteBoard);
            gameRepo.save(game);
            userRepo.save(user);
            shipRepo.save(ship);
            roundRepo.save(round);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return "OK";
        }else{
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
                if(!round.isImmediateCard()) {
                    game.collectPoints();
                    roundService.addRound(game.getId());
                }
            }
            user=userRepo.save(user);
            gameRepo.save(game);
            roundRepo.save(round);
            int counterCard = user.getMarketCards().size()-1;
            marketCardRepository.save(user.getMarketCards().get(counterCard));
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return "OK";
        }else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "NullException";
        }
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/marketcard", method = RequestMethod.PUT)
    public String playMarketCard(HttpServletResponse response,@PathVariable Long gameId,@PathVariable Long roundId,@RequestParam("playerToken") String playerToken,
    @RequestParam("marketCardId") Long marketCardId) {
        Game game = gameRepo.findOne(gameId);
        User user = userRepo.findByToken(playerToken);
        Round round = roundRepo.findById(roundId);
        AMarketCard marketCard = marketCardRepository.findById(marketCardId);
        if(marketCard instanceof MCDecoration || marketCard instanceof Statue){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "CardCanNotBePlayedException";
        }
        if(game != null && user != null && round != null){
            PlayMarketCardMove move = new PlayMarketCardMove(user, round, game, marketCard);
            try{
                validatorManager.validateSync(game,move);
                moveRepo.save(move);
            } catch (ValidationException e){
                validationExceptionHandler(e,response);
                return e.getMessage();
            }
            moveService.playMarketCard(game,move);
            Market market = siteBoardsService.getMarket(game);
            if(market.getUserColor().isEmpty()&&market.isOccupied()&&!round.isImmediateCard()){
                game.collectPoints();
                roundService.addRound(game.getId());
            }
            game.collectPoints();
            marketCard.setPlayed(true);
            marketCardRepository.save(marketCard);
            user=userRepo.save(user);
            gameRepo.save(game);
            roundRepo.save(round);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return "OK";
        }else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "NullException";
        }
    }
    @RequestMapping(value = CONTEXT + "/{gameId}/rounds/{roundId}/lever", method = RequestMethod.PUT)
    public String playLeverCard(HttpServletResponse response,@PathVariable Long gameId,@PathVariable Long roundId,@RequestParam("playerToken") String playerToken,
                                 @RequestParam("userColors") List<String> userColors) {
        Game game = gameRepo.findOne(gameId);
        User user = userRepo.findByToken(playerToken);
        Round round = roundRepo.findById(roundId);

        if(game != null && user != null && round != null){
            Stone[] tmpStones = new Stone[userColors.size()];
            for(int i = 0;i<tmpStones.length;i++){
                tmpStones[i] = new Stone(userColors.get(i));
            }
            PlayLeverCardMove move = new PlayLeverCardMove(user,round,game,tmpStones);
            moveRepo.save(move);
            //VALIDATOR
            moveService.playLeverCard(game,move);
            moveService.addLeverUser(game);
//            game.collectPoints();
//            roundService.addRound(game.getId());
            round.setActionCardLever(false);
            }
        userRepo.save(user);
        gameRepo.save(game);
        roundRepo.save(round);

        return null;
    }
}

