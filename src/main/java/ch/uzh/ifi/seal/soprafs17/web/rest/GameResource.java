package ch.uzh.ifi.seal.soprafs17.web.rest;

import java.util.ArrayList;
import java.util.List;

import ch.uzh.ifi.seal.soprafs17.DTOs.GameDTO;
import ch.uzh.ifi.seal.soprafs17.Ships.OneSeatedShip;
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
import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.Move;
import ch.uzh.ifi.seal.soprafs17.entity.User;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.repository.UserRepository;

// For this controlles the correspndant service is missing
// Todo create a GameService in which you implement the logic of the game
// You can refer to the UserService as example
// per heroku :D

@RestController
public class GameResource extends GenericResource {

    Logger logger  = LoggerFactory.getLogger(GameResource.class);

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private GameRepository gameRepo;

    private final String CONTEXT = "/games";

    /*
     * Context: /games
     */

    @RequestMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<GameDTO> listGames() {
        logger.debug("listGames");
        // List<Game> result = new ArrayList<>();
        // gameRepo.findAll().forEach(result::add);
        // return result;
        List<GameDTO> GameDTOS = new ArrayList<>();
        for(Game g: gameRepo.findAll()){
            GameDTO gameDTO = new GameDTO(g.getId(),g.getName(),g.getOwner(),g.getStatus(),g.getCurrentPlayer());
            GameDTOS.add(gameDTO);
        }
        return GameDTOS;


    }

    @RequestMapping(value = CONTEXT, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String addGame(@RequestBody Game game, @RequestParam("token") String userToken) {
        logger.debug("addGame: " + game);

        User owner = userRepo.findByToken(userToken);
        if (owner != null) {
            // TODO Mapping into Game
            game = gameRepo.save(game);

            return CONTEXT + "/" + game.getId();
        }

        return null;
    }

    /*
     * Context: /game/{game-id}
     */
    @RequestMapping(value = CONTEXT + "game/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public GameDTO getGame(@PathVariable Long gameId) {
        logger.debug("getGame: " + gameId);

        Game game = gameRepo.findOne(gameId);
        GameDTO gameDTO = new GameDTO(game.getId(),game.getName(),game.getOwner(),game.getStatus(),game.getCurrentPlayer());

        return gameDTO;
    }

    @RequestMapping(value = CONTEXT + "game/{gameId}/start", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void startGame(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        logger.debug("startGame: " + gameId);

        Game game = gameRepo.findOne(gameId);
        User owner = userRepo.findByToken(userToken);


        //the game can be started only from the owner
        if (owner != null && game != null && game.getOwner().equals(owner.getUsername())) {

            // TODO: Start game in GameService
        }
    }

    @RequestMapping(value = CONTEXT + "game/{gameId}/stop", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void stopGame(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        logger.debug("stopGame: " + gameId);

        Game game = gameRepo.findOne(gameId);
        User owner = userRepo.findByToken(userToken);

        if (owner != null && game != null && game.getOwner().equals(owner.getUsername())) {
            // TODO: Stop game in GameService
        }
    }

    /*
     * Context: /game/{game-id}/move
     */
    @RequestMapping(value = CONTEXT + "game/{gameId}/move")
    @ResponseStatus(HttpStatus.OK)
    public List<Move> listMoves(@PathVariable Long gameId) {
        logger.debug("listMoves");

        Game game = gameRepo.findOne(gameId);
        if (game != null) {
            return game.getMoves();
        }

        return null;
    }

    @RequestMapping(value = CONTEXT + "game/{gameId}/move", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addMove(@RequestBody Move move) {
        logger.debug("addMove: " + move);
        // TODO Mapping into Move + execution of move
    }

    @RequestMapping(value = CONTEXT + "game/{gameId}/move/{moveId}")
    @ResponseStatus(HttpStatus.OK)
    public Move getMove(@PathVariable Long gameId, @PathVariable Integer moveId) {
        logger.debug("getMove: " + gameId);

        Game game = gameRepo.findOne(gameId);
        if (game != null) {
            return game.getMoves().get(moveId);
        }

        return null;
    }

    /*
     * Context: /game/{game-id}/player
     */
    @RequestMapping(value = CONTEXT + "game/{gameId}/player")
    @ResponseStatus(HttpStatus.OK)
    public List<User> listPlayers(@PathVariable Long gameId) {
        logger.debug("listPlayers");

        Game game = gameRepo.findOne(gameId);
        if (game != null) {
            return game.getPlayers();
        }

        return null;
    }

    @RequestMapping(value = CONTEXT + "game/{gameId}/player", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String addPlayer(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        logger.debug("addPlayer: " + userToken);

        Game game = gameRepo.findOne(gameId);
        User player = userRepo.findByToken(userToken);

        if (game != null && player != null
                && game.getPlayers().size() < GameConstants.MAX_PLAYERS) {
            game.getPlayers().add(player);
            logger.debug("Game: " + game.getName() + " - player added: " + player.getUsername());
            return CONTEXT + "/" + gameId + "/player/" + (game.getPlayers().size() - 1);
        } else {
            logger.error("Error adding player with token: " + userToken);
        }
        return null;
    }

    @RequestMapping(value = CONTEXT + "game/{gameId}/player/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public User getPlayer(@PathVariable Long gameId, @PathVariable Integer playerId) {

        Game game = gameRepo.findOne(gameId);

        return game.getPlayers().get(playerId);
    }

}