package ch.uzh.ifi.seal.soprafs17.web.rest;

import java.util.ArrayList;
import java.util.List;

import ch.uzh.ifi.seal.soprafs17.model.DTOs.GameDTO;
import ch.uzh.ifi.seal.soprafs17.model.DTOs.UserDTO;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
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
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.Move;
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

    private final String CONTEXT = "/games";

    /*
     * Context: /games
     */
    @RequestMapping(value = CONTEXT)
    @ResponseStatus(HttpStatus.OK)
    public List<GameDTO> listGames() {
        //public GameDTO(Long id, String name, Long owner, String status, Long currentPlayer, Long nextPlayer,
        //List<Long> rounds, List<Long> players, List<Long> siteBoards) {

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
                    //UserDTO(Long id, String name, String username, String token, UserStatus status, List<Long> games, List<Long> moves, String color)

                    List<Long> playerGamesDTO = new ArrayList<>();
                    if(u.getGames()!=null) {
                        for (Game pg : u.getGames()) {
                            playerGamesDTO.add(pg.getId());
                        }
                    }

                    List<Long> playerMovesDTO = new ArrayList<>();
                    if(u.getMoves()!=null) {
                        for (Move pm : u.getMoves()) {
                            playerMovesDTO.add(pm.getId());
                        }
                    }
                    playersDTO.add(new UserDTO(u.getId(),u.getName(),u.getUsername(),u.getToken(),u.getStatus(),playerGamesDTO,playerMovesDTO,u.getColor()));
                }
                if(g.getSiteBoards()!=null) {
                    for (SiteBoard s : g.getSiteBoards()) {
                        siteBoardsId.add(s.getId());
                    }
                }
                if(g.getNextPlayer()!=null) {
                    gamesDTO.add(new GameDTO(g.getId(), g.getName(), g.getOwner(), g.getStatus(), g.getCurrentPlayer().getId(),
                            g.getNextPlayer().getId(), roundsId, playersDTO, siteBoardsId));
                }
                else{
                    gamesDTO.add(new GameDTO(g.getId(), g.getName(), g.getOwner(), g.getStatus(), g.getCurrentPlayer().getId(),
                            null, roundsId, playersDTO, siteBoardsId));
                }
            }
            return gamesDTO;
    }

    @RequestMapping(value = CONTEXT, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String addGame(@RequestBody Game game, @RequestParam("token") String userToken) {
        logger.debug("addGame: " + game);
        String addedGame = gameService.addGame(game, userToken);
        if (game == null) {
            return null;
        } else {
            return addedGame;
        }
    }

    /*
     * Context: /game/{game-id}
     */
    @RequestMapping(value = CONTEXT + "/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public GameDTO getGame(@PathVariable Long gameId) {
        logger.debug("getGame: " + gameId);

        Game g  = gameService.getGame(gameId);
        List<Long> roundsId = new ArrayList<>();
        List<Long> playersId = new ArrayList<>();
        List<Long> siteBoardsId = new ArrayList<>();
        List<UserDTO> playersDTO = new ArrayList<>();
        for (Round r : g.getRounds()) {
            roundsId.add(r.getId());
        }
        for (User u : g.getPlayers()) {
            List<Long> playerGamesDTO = new ArrayList<>();
            for(Game pg : u.getGames()){
                playerGamesDTO.add(pg.getId());
            }
            List<Long> playerMovesDTO = new ArrayList<>();
            for(Move pm : u.getMoves()){
                playerMovesDTO.add(pm.getId());
            }

            playersDTO.add(new UserDTO(u.getId(),u.getName(),u.getUsername(),u.getToken(),u.getStatus(),playerGamesDTO,playerMovesDTO,u.getColor()));
        }
        for (SiteBoard s : g.getSiteBoards()) {
            siteBoardsId.add(s.getId());
        }
        if(g.getNextPlayer()!=null) {
            return new GameDTO(g.getId(), g.getName(), g.getOwner(), g.getStatus(), g.getCurrentPlayer().getId(),
                    g.getNextPlayer().getId(), roundsId, playersDTO, siteBoardsId);
        }else{
            return new GameDTO(g.getId(), g.getName(), g.getOwner(), g.getStatus(), g.getCurrentPlayer().getId(),
                    null, roundsId, playersDTO, siteBoardsId);
        }

    }

    @RequestMapping(value = CONTEXT + "/{gameId}/start", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void startGame(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        logger.debug("startGame: " + gameId);

        gameService.startGame(gameId, userToken);
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/stop", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void stopGame(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        logger.debug("stopGame: " + gameId);

        gameService.stopGame(gameId, userToken);
    }

    /*
     * Context: /game/{game-id}/move
     */
    @RequestMapping(value = CONTEXT + "/{gameId}/move")
    @ResponseStatus(HttpStatus.OK)
    public List<Move> listMoves(@PathVariable Long gameId) {
        logger.debug("listMoves");

        return gameService.listMoves(gameId);
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/move", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addMove(@RequestBody Move move) {
        logger.debug("addMove: " + move);
        // TODO Mapping into Move + execution of move
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/move/{moveId}")
    @ResponseStatus(HttpStatus.OK)
    public Move getMove(@PathVariable Long gameId, @PathVariable Integer moveId) {
        logger.debug("getMove: " + gameId);

        return gameService.getMove(gameId, moveId);
    }

    /*
     * Context: /game/{game-id}/player
     */
    @RequestMapping(value = CONTEXT + "/{gameId}/player")
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
            for(Move m : u.getMoves()){
                movesId.add(m.getId());
            }
            //UserDTO(Long id, String name, String username, String token, UserStatus status, List<Long> games, List<Long> moves, String color)
            usersDTO.add(new UserDTO(u.getId(),u.getName(),u.getUsername(),u.getToken(),u.getStatus(),gamesId,movesId,u.getColor()));
        }
        return usersDTO;
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/player", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String addUser(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        logger.debug("addPlayer: " + userToken);

        return gameService.addUser(gameId, userToken);
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/player/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getPlayer(@PathVariable Long gameId, @PathVariable Integer playerId) {

        User u = gameService.getPlayer(gameId, playerId);
        List<Long> gamesId = new ArrayList<>();
        List<Long> movesId = new ArrayList<>();
        for(Game g : u.getGames()){
            gamesId.add(g.getId());
        }
        for(Move m : u.getMoves()){
            movesId.add(m.getId());
        }
        return new UserDTO(u.getId(),u.getName(),u.getUsername(),u.getToken(),u.getStatus(),gamesId,movesId,u.getColor());
    }
    //when the user joins a game, he becomes a Player.
    @RequestMapping(value = CONTEXT + "/{gameId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createPlayer(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        gameService.createPlayer(gameId, userToken);
    }
}