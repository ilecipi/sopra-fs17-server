package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.User;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 * Created by erion on 13.03.17.
 */
public class PlayerService {

    //Logger log = LoggerFactory.getLogger(PlayerService.class);

    @Autowired
    GameRepository gameRepo;
    @Autowired
    UserRepository userRepo;

    private final String PATH = "/games";

    //when the user joins a game, he becomes a Player.
    @RequestMapping(value = PATH + "/{gameId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createPlayer(@PathVariable Long gameId,@RequestParam("token") String userToken, @RequestParam("color") String color) {

        User user = userRepo.findByToken(userToken);
        Game game = gameRepo.findOne(gameId);

        //check whether the color choosen is already taken
        if (user.equals(game.getCurrentPlayer()) && !game.getColors().get(color)) {
            user.setColor(color);
            game.getColors().put(color,true);
            game.setCurrentPlayer(game.getCurrentPlayer()+1);
        }

        gameRepo.save(game);
        userRepo.save(user);
    }
}
