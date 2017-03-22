package ch.uzh.ifi.seal.soprafs17.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.SiteBoardsService;
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

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Move;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;

// For this controlles the correspndant service is missing
// Todo create a GameService in which you implement the logic of the game
// You can refer to the UserService as example
// per heroku :D

@RestController
public class GameResource extends GenericResource {

    Logger logger = LoggerFactory.getLogger(GameResource.class);

    @Autowired
    GameService gameService;

    private final String CONTEXT = "/games";

    /*
     * Context: /games
     */
    @RequestMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> listGames() {
        logger.debug("listGames");
        return gameService.listGames();
    }

    @RequestMapping(value = CONTEXT, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String addGame(@RequestBody Game game, @RequestParam("token") String userToken) {
        logger.debug("addGame: " + game);
        String addedGame = gameService.addGame(game, userToken);
        if (game == null) {
            return null;
        } else {
            return CONTEXT + addedGame;
        }
    }

    /*
     * Context: /game/{game-id}
     */
    @RequestMapping(value = CONTEXT + "/game/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public Game getGame(@PathVariable Long gameId) {
        logger.debug("getGame: " + gameId);

        return gameService.getGame(gameId);
    }

    @RequestMapping(value = CONTEXT + "/game/{gameId}/start", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void startGame(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        logger.debug("startGame: " + gameId);

        gameService.startGame(gameId, userToken);
    }

    @RequestMapping(value = CONTEXT + "/game/{gameId}/stop", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void stopGame(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        logger.debug("stopGame: " + gameId);

        gameService.stopGame(gameId, userToken);
    }

    /*
     * Context: /game/{game-id}/move
     */
    @RequestMapping(value = CONTEXT + "/game/{gameId}/move")
    @ResponseStatus(HttpStatus.OK)
    public List<Move> listMoves(@PathVariable Long gameId) {
        logger.debug("listMoves");

        return gameService.listMoves(gameId);
    }

    @RequestMapping(value = CONTEXT + "/game/{gameId}/move", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addMove(@RequestBody Move move) {
        logger.debug("addMove: " + move);
        // TODO Mapping into Move + execution of move
    }

    @RequestMapping(value = CONTEXT + "/game/{gameId}/move/{moveId}")
    @ResponseStatus(HttpStatus.OK)
    public Move getMove(@PathVariable Long gameId, @PathVariable Integer moveId) {
        logger.debug("getMove: " + gameId);

        return gameService.getMove(gameId, moveId);
    }

    /*
     * Context: /game/{game-id}/player
     */
    @RequestMapping(value = CONTEXT + "/game/{gameId}/player")
    @ResponseStatus(HttpStatus.OK)
    public List<User> listPlayers(@PathVariable Long gameId) {
        logger.debug("listPlayers");
        return gameService.listPlayers(gameId);
    }

    @RequestMapping(value = CONTEXT + "/game/{gameId}/player", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String addUser(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        logger.debug("addPlayer: " + userToken);

        return gameService.addUser(gameId, userToken);
    }

    @RequestMapping(value = CONTEXT + "/game/{gameId}/player/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public User getPlayer(@PathVariable Long gameId, @PathVariable Integer playerId) {

        return gameService.getPlayer(gameId, playerId);
    }

    //when the user joins a game, he becomes a Player.
    @RequestMapping(value = CONTEXT + "/game/{gameId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createPlayer(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        gameService.createPlayer(gameId, userToken);
    }
}