package ch.uzh.ifi.seal.soprafs17.web.rest;

import java.util.ArrayList;
import java.util.List;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.GameDTO;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.UserDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.ruleEngine.RuleBook;
import ch.uzh.ifi.seal.soprafs17.service.validatorEngine.ValidatorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;

// For this controlles the correspndant service is missing
// Todo create a GameService in which you implement the logic of the game
// You can refer to the UserService as example
// per heroku :D

@RestController
public class GameResource extends GenericResource {

    Logger logger = LoggerFactory.getLogger(GameResource.class);

    @Autowired
    GameService gameService;

    @Autowired
    RuleBook ruleBook;

    @Autowired
    ValidatorManager validatorBook;

    private final String CONTEXT = "/games";

    @RequestMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<GameDTO> listGames() {
        logger.debug("listGames");
        List<Game> games = gameService.listGames();
        List<GameDTO> gamesDTO = new ArrayList<>();
            for (Game g : games) {
                List<Long> roundsId = new ArrayList<>();
                List<Long> siteBoardsId = new ArrayList<>();
                List<UserDTO> playersDTO = new ArrayList<>();
                for (Round r : g.getRounds()) {
                    roundsId.add(r.getId());
                }
                for (User u : g.getPlayers()) {

                    List<Long> playerGamesDTO = new ArrayList<>();
                    if(u.getGames()!=null) {
                        for (Game pg : u.getGames()) {
                            playerGamesDTO.add(pg.getId());
                        }
                    }

                    List<Long> playerMovesDTO = new ArrayList<>();
                    if(u.getAMoves()!=null) {
                        for (AMove pm : u.getAMoves()) {
                            playerMovesDTO.add(pm.getId());
                        }
                    }
                    playersDTO.add(new UserDTO(u.getId(),u.getName(),u.getUsername(),u.getToken(),u.getStatus(),playerGamesDTO,playerMovesDTO,u.getColor(),u.getSupplySled(),u.getMarketCards(),u.getStoneQuarry()));
                }
                if(g.getSiteBoards()!=null) {
                    for (SiteBoard s : g.getSiteBoards()) {
                        siteBoardsId.add(s.getId());
                    }
                }
                if(g.getNextPlayer()!=null) {
                    if(g.getCurrentRound()!=null) {
                        gamesDTO.add(new GameDTO(g.getId(), g.getName(), g.getOwner(), g.getStatus(), g.getCurrentPlayer().getId(),
                                g.getNextPlayer().getId(), roundsId, playersDTO, siteBoardsId, g.getPoints(), g.getMarketCards(),g.getCurrentRound().isActionCardHammer()
                        ,g.getCurrentRound().getListActionCardLever(),g.getCurrentRound().getIsActionCardChisel(),g.getCurrentRound().getIsActionCardSail(),g.getDiscardedCardsCounter(),g.getCurrentRound().isImmediateCard()));
                    }else{
                        gamesDTO.add(new GameDTO(g.getId(), g.getName(), g.getOwner(), g.getStatus(), g.getCurrentPlayer().getId(),
                                g.getNextPlayer().getId(), roundsId, playersDTO, siteBoardsId, g.getPoints(), g.getMarketCards(),false,null,0,0,0,false));
                    }
                }
                else{
                    if(g.getCurrentRound()!=null) {
                        gamesDTO.add(new GameDTO(g.getId(), g.getName(), g.getOwner(), g.getStatus(), g.getCurrentPlayer().getId(),
                                null, roundsId, playersDTO, siteBoardsId, g.getPoints(), g.getMarketCards(),g.getCurrentRound().isActionCardHammer()
                                ,g.getCurrentRound().getListActionCardLever(),g.getCurrentRound().getIsActionCardChisel(),g.getCurrentRound().getIsActionCardSail(),g.getDiscardedCardsCounter(),g.getCurrentRound().isImmediateCard()));
                    }else{
                        gamesDTO.add(new GameDTO(g.getId(), g.getName(), g.getOwner(), g.getStatus(), g.getCurrentPlayer().getId(),
                                null, roundsId, playersDTO, siteBoardsId, g.getPoints(), g.getMarketCards(),false,null,0,0,0,false));
                    }
                }
            }
            return gamesDTO;
    }

    @RequestMapping(value = CONTEXT, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public synchronized Game addGame(@RequestBody Game game, @RequestParam("token") String userToken) {
        logger.debug("addGame: " + game);
        Game addedGame = gameService.addGame(game, userToken);
        if (game == null) {
            return null;
        } else {
            return addedGame;
        }
    }

    @RequestMapping(value = CONTEXT + "/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public GameDTO getGame(@PathVariable Long gameId) {
        logger.debug("getGame: " + gameId);
        Game g  = gameService.getGame(gameId);
        if(g!=null) {
            List<Long> roundsId = new ArrayList<>();
            List<Long> playersId = new ArrayList<>();
            List<Long> siteBoardsId = new ArrayList<>();
            List<UserDTO> playersDTO = new ArrayList<>();
            for (Round r : g.getRounds()) {
                roundsId.add(r.getId());
            }
            for (User u : g.getPlayers()) {
                List<Long> playerGamesDTO = new ArrayList<>();
                for (Game pg : u.getGames()) {
                    playerGamesDTO.add(pg.getId());
                }
                List<Long> playerMovesDTO = new ArrayList<>();
                for (AMove pm : u.getAMoves()) {
                    playerMovesDTO.add(pm.getId());
                }
                playersDTO.add(new UserDTO(u.getId(), u.getName(), u.getUsername(), u.getToken(), u.getStatus(), playerGamesDTO, playerMovesDTO, u.getColor(), u.getSupplySled(),u.getMarketCards(),u.getStoneQuarry()));
            }
            for (SiteBoard s : g.getSiteBoards()) {
                siteBoardsId.add(s.getId());
            }
            if (g.getNextPlayer() != null) {
                if (g.getCurrentRound()!=null) {
                    return new GameDTO(g.getId(), g.getName(), g.getOwner(), g.getStatus(), g.getCurrentPlayer().getId(),
                            g.getNextPlayer().getId(), roundsId, playersDTO, siteBoardsId, g.getPoints(), g.getMarketCards(), g.getCurrentRound().isActionCardHammer()
                            , g.getCurrentRound().getListActionCardLever(), g.getCurrentRound().getIsActionCardChisel(), g.getCurrentRound().getIsActionCardSail(),g.getDiscardedCardsCounter(),g.getCurrentRound().isImmediateCard());
                }else{
                    return new GameDTO(g.getId(), g.getName(), g.getOwner(), g.getStatus(), g.getCurrentPlayer().getId(),
                            g.getNextPlayer().getId(), roundsId, playersDTO, siteBoardsId, g.getPoints(), g.getMarketCards(),false,null,0,0,0,false);
                }
            } else {
                if (g.getCurrentRound()!=null) {
                    return new GameDTO(g.getId(), g.getName(), g.getOwner(), g.getStatus(), g.getCurrentPlayer().getId(),
                            null, roundsId, playersDTO, siteBoardsId, g.getPoints(), g.getMarketCards(), g.getCurrentRound().isActionCardHammer()
                            ,g.getCurrentRound().getListActionCardLever(), g.getCurrentRound().getIsActionCardChisel(), g.getCurrentRound().getIsActionCardSail(),g.getDiscardedCardsCounter(),g.getCurrentRound().isImmediateCard());
                }else{
                    return new GameDTO(g.getId(), g.getName(), g.getOwner(), g.getStatus(), g.getCurrentPlayer().getId(),
                            null, roundsId, playersDTO, siteBoardsId, g.getPoints(), g.getMarketCards(),false,null,0,0,0, false);
                }
            }
        }
        return null;
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/start", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public synchronized void startGame(@PathVariable Long gameId, @RequestParam("playerToken") String userToken) {
        logger.debug("startGame: " + gameId);
        gameService.startGame(gameId, userToken);
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/players")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> listPlayers(@PathVariable Long gameId) {
        logger.debug("listPlayers");
        List<User> users = gameService.listPlayers(gameId);
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User u : users){
            List<Long> gamesId = new ArrayList<>();
            List<Long> movesId = new ArrayList<>();
            for(Game g : u.getGames()){
                gamesId.add(g.getId());
            }
            for(AMove m : u.getAMoves()){
                movesId.add(m.getId());
            }
            //UserDTO(Long id, String name, String username, String token, UserStatus status, List<Long> games, List<Long> moves, String color)
            usersDTO.add(new UserDTO(u.getId(),u.getName(),u.getUsername(),u.getToken(),u.getStatus(),gamesId,movesId,u.getColor(),u.getSupplySled(),u.getMarketCards(),u.getStoneQuarry()));
        }
        return usersDTO;
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/player", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public synchronized String addUser(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        logger.debug("addPlayer: " + userToken);

        return gameService.addUser(gameId, userToken);
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/players/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getPlayer(@PathVariable Long gameId, @PathVariable Integer playerId) {

        User u = gameService.getPlayer(gameId, playerId);
        List<Long> gamesId = new ArrayList<>();
        List<Long> movesId = new ArrayList<>();
        for(Game g : u.getGames()){
            gamesId.add(g.getId());
        }
        for(AMove m : u.getAMoves()){
            movesId.add(m.getId());
        }
        return new UserDTO(u.getId(),u.getName(),u.getUsername(),u.getToken(),u.getStatus(),gamesId,movesId,u.getColor(),u.getSupplySled(),u.getMarketCards(),u.getStoneQuarry());
    }
    //when the user joins a game, he becomes a Player.
    @RequestMapping(value = CONTEXT + "/{gameId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createPlayer(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        gameService.createPlayer(gameId, userToken);
    }

    //fastforward game to 6th round
    @RequestMapping(value = CONTEXT + "/{gameId}/fastforward", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void fastForward(@PathVariable Long gameId) {

        gameService.fastForward(gameId);
        gameService.fastForward(gameId);
        gameService.fastForward(gameId);
        gameService.fastForward(gameId);
        gameService.fastForward(gameId);
    }

    //fastforward only 1 round
    @RequestMapping(value = CONTEXT + "/{gameId}/fastforwardoneround", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void fastForwardOneRound(@PathVariable Long gameId) {
        gameService.fastForward(gameId);
    }
}