package ch.uzh.ifi.seal.soprafs17.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
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
    public List<Game> listGames() {
        logger.debug("listGames");
        List<Game> result = new ArrayList<>();
        gameRepo.findAll().forEach(result::add);
        return result;
    }

    @RequestMapping(value = CONTEXT, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String addGame(@RequestBody Game game, @RequestParam("token") String userToken) {
        logger.debug("addGame: " + game);

        User owner = userRepo.findByToken(userToken);
        if (owner != null) {

            owner = userRepo.save(owner);
            game = gameRepo.save(game);
            game.setOwner(owner.getUsername());
            game.setCurrentPlayer(owner);
            game.setStatus(GameStatus.PENDING);
            owner.getGames().add(game);
            owner.setStatus(UserStatus.ONLINE);
            owner = userRepo.save(owner);
            game = gameRepo.save(game);

            return CONTEXT + "/" + game.getId();
        }

        return null;
    }

    /*
     * Context: /game/{game-id}
     */
    @RequestMapping(value = CONTEXT + "/game/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public Game getGame(@PathVariable Long gameId) {
        logger.debug("getGame: " + gameId);

        Game game = gameRepo.findOne(gameId);

        return game;
    }

    @RequestMapping(value = CONTEXT + "/game/{gameId}/start", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void startGame(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        logger.debug("startGame: " + gameId);

        Game game = gameRepo.findOne(gameId);
        User owner = userRepo.findByToken(userToken);


        //the game can be started only from the owner
        if (owner != null && game != null && game.getOwner().equals(owner.getUsername())
                && game.getPlayers().size()>=GameConstants.MIN_PLAYERS&&game.getPlayers().size()<=GameConstants.MAX_PLAYERS){
            game.setCurrentPlayer(owner);
            System.out.println("START GAME INIZIATO");
            // TODO: Start game in GameService
            game.setNextPlayer(game.getPlayers().get(game.getPlayers().indexOf(game.getCurrentPlayer())+1));
            game.setStatus(GameStatus.RUNNING);
            for(User u : game.getPlayers()){
                u.setStatus(UserStatus.IS_PLAYING);
                userRepo.save(u);
            }
            gameRepo.save(game);
        }
    }

    @RequestMapping(value = CONTEXT + "/game/{gameId}/stop", method = RequestMethod.POST)
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
    @RequestMapping(value = CONTEXT + "/game/{gameId}/move")
    @ResponseStatus(HttpStatus.OK)
    public List<Move> listMoves(@PathVariable Long gameId) {
        logger.debug("listMoves");

        Game game = gameRepo.findOne(gameId);
        if (game != null) {
            return game.getMoves();
        }

        return null;
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

        Game game = gameRepo.findOne(gameId);
        if (game != null) {
            return game.getMoves().get(moveId);
        }

        return null;
    }

    /*
     * Context: /game/{game-id}/player
     */
    @RequestMapping(value = CONTEXT + "/game/{gameId}/player")
    @ResponseStatus(HttpStatus.OK)
    public List<User> listPlayers(@PathVariable Long gameId) {
        logger.debug("listPlayers");

        Game game = gameRepo.findOne(gameId);
        if (game != null) {
            return game.getPlayers();
        }

        return null;
    }

    @RequestMapping(value = CONTEXT + "/game/{gameId}/player", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String addUser(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        logger.debug("addPlayer: " + userToken);

        Game game = gameRepo.findOne(gameId);
        User player = userRepo.findByToken(userToken);

        if (game != null && player != null && game.getPlayers().size() < GameConstants.MAX_PLAYERS) {
            player.getGames().add(game);
            player.setStatus(UserStatus.ONLINE);
            game.getPlayers().add(player);
            userRepo.save(player);
            gameRepo.save(game);
            logger.debug("Game: " + game.getName() + " - player added: " + player.getUsername());
            return CONTEXT + "/" + gameId + "/player/" + (game.getPlayers().size() - 1);
        } else {
            logger.error("Error adding player with token: " + userToken);
        }
        return null;
    }

    @RequestMapping(value = CONTEXT + "/game/{gameId}/player/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public User getPlayer(@PathVariable Long gameId, @PathVariable Integer playerId) {

        Game game = gameRepo.findOne(gameId);

        return game.getPlayers().get(playerId);
    }

    //when the user joins a game, he becomes a Player.
    @RequestMapping(value = CONTEXT + "/game/{gameId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createPlayer(@PathVariable Long gameId,@RequestParam("token") String userToken) {

        User user = userRepo.findByToken(userToken);
        Game game = gameRepo.findOne(gameId);
        if(game.getPlayers().contains(user)) {
            //assign color to user
            if (true) {
                boolean colorNotChosen = true;
                String color;
                while (colorNotChosen) {
                    Random rn = new Random();
                    int i = rn.nextInt() % 4;
                    if (i == 0) {
                        color = "black";
                    } else if (i == 1) {
                        color = "white";
                    } else if (i == 2) {
                        color = "brown";
                    } else {
                        color = "grey";
                    }
                    if (!game.getColors().get(color)) {
                        user.setColor(color);
                        game.getColors().put(color, true);
                        user.setStatus(UserStatus.IS_READY);
                        colorNotChosen = false;

                    }
                }

            }

//        }
            gameRepo.save(game);
            userRepo.save(user);
        }
    }

}