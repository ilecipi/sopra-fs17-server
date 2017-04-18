package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.model.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.RuleBook;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.ValidatorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by erion on 13.03.17.
 */
@Service("gameService")
@Transactional
public class GameService {

    private final Logger logger = LoggerFactory.getLogger(GameService.class);
    private final String CONTEXT = "/games";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private SiteBoardsService siteBoardsService;

    @Autowired
    private ShipService shipService;

    @Autowired
    private RoundService roundService;
    @Autowired
    private RuleBook ruleBook;
    @Autowired
    private ValidatorManager validatorManager;

    private static int counter = 1;

    public UserRepository getUserRepo() {
        return userRepository;
    }

    public GameRepository getGameRepo() {
        return gameRepository;
    }

    public ShipService getShipService() {
        return shipService;
    }

    public List<Game> listGames(){
        List<Game> result = new ArrayList<>();
        gameRepository.findAll().forEach(result::add);
        return result;
    }

    public Game addGame(Game game, String userToken){
        User owner = userRepository.findByToken(userToken);
        if (owner != null&&owner.getStatus()==UserStatus.ONLINE) {
            game.setName("Game " + counter++);
            game.setOwner(owner.getUsername());
            game.setCurrentPlayer(owner);
            game.setStatus(GameStatus.PENDING);
            game = gameRepository.save(game);
            owner.getGames().add(game);
            owner.setStatus(UserStatus.IN_A_LOBBY);
            userRepository.save(owner);
//            gameRepository.save(game);

//            return "/" + game.getId();
            return game;
        }

        return null;
    }

    public Game getGame(Long gameId){
        return gameRepository.findOne(gameId);
    }

    public void startGame(Long gameId, String userToken){

        Game game = gameRepository.findOne(gameId);
        User owner = userRepository.findByToken(userToken);

        //the game can be started only from the owner
        if (owner != null && game != null && game.getOwner().equals(owner.getUsername())
                && game.getPlayers().size()>= GameConstants.MIN_PLAYERS&&game.getPlayers().size()<=GameConstants.MAX_PLAYERS
                && game.getStatus() != GameStatus.RUNNING){

            //The game cannot start if not every player is ready
            boolean allPlayersReady = true;
            for(User u : game.getPlayers()){
                if(u.getStatus()!=UserStatus.IS_READY){
                    allPlayersReady=false;
                }
            }
            if(allPlayersReady) {
                siteBoardsService.addTemple(game.getId());
                siteBoardsService.addPyramid(game.getId());
                siteBoardsService.addObelisk(game.getId());
                siteBoardsService.addBurialChamber(game.getId());
                siteBoardsService.addMarket(game.getId());
                game.initShipsCards();
                game.initMarketCards();

                //TODO: DELETE TESTING BEFORE DEADLINE
                //for testing
                Random rn = new Random();
                game=gameRepository.save(game);
                roundService.addRounds(game.getId());
                //add a round to the game
                game.setCurrentPlayer(owner);
                // TODO: Start game in GameService
                game.setStatus(GameStatus.RUNNING);
                int initStones=2;
                int initStoneQuarry=28;
                for (User u : game.getPlayers()) {
                    game.getPoints().put(u.getColor(),/*0*/rn.nextInt(100) );
                    u.setStatus(UserStatus.IS_PLAYING);
                    u.setSupplySled(initStones++);
                    u.setStoneQuarry(initStoneQuarry--);
                    userRepository.save(u);
                }

                gameRepository.save(game);
            }
        }
    }

    public List<User> listPlayers(Long gameId){
        Game game = gameRepository.findOne(gameId);
        if (game != null) {
            return game.getPlayers();
        }

        return null;
    }

    public String addUser(Long gameId,String userToken){
        Game game = gameRepository.findOne(gameId);
        User player = userRepository.findByToken(userToken);

        if (game != null && player != null && game.getPlayers().size() < GameConstants.MAX_PLAYERS
                &&player.getStatus()==UserStatus.ONLINE) {
            player.getGames().add(game);
            player.setStatus(UserStatus.IN_A_LOBBY);
            if(game.getPlayers().size()==1){                //Set the second player as the nextPlayer
                game.setNextPlayer(player);
            }
            game.getPlayers().add(player);
            userRepository.save(player);
            gameRepository.save(game);
            logger.debug("Game: " + game.getName() + " - player added: " + player.getUsername());
            return CONTEXT + "/" + gameId + "/player/" + (game.getPlayers().size());
        } else {
            logger.error("Error adding player with token: " + userToken);

        }
        return null;
    }

    public User getPlayer(Long gameId,Integer playerId){
        Game game = gameRepository.findOne(gameId);

        return game.getPlayers().get(playerId);
    }

    public void createPlayer(Long gameId, String userToken){

        User user = userRepository.findByToken(userToken);
        Game game = gameRepository.findOne(gameId);
        //assign color to user
        if (game.getPlayers().contains(user)) {
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
        gameRepository.save(game);
        userRepository.save(user);
    }

    public Game setNextPlayer(Game game){
        int indexOfCurrentPlayer= game.getPlayers().indexOf(game.getCurrentPlayer());
        int indexOfNextPlayer=(indexOfCurrentPlayer+1)%game.getPlayers().size();
        game.setCurrentPlayer(game.getPlayers().get(indexOfNextPlayer));
        game.setNextPlayer(game.getPlayers().get(indexOfNextPlayer%game.getPlayers().size()));
        gameRepository.save(game);

        return game;
    }
}
