package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;

/**
 * Created by ilecipi on 03.04.17.
 */
public class BasicValidation{

    public static boolean checkCurrentUser(Game game, User user){
        if(game.getCurrentPlayer().getToken().equals(user.getToken())){
            return true;
        }else{
            return false;
        }
    }

    public static boolean checkCurrentRound(Game game, Round round){
        int currentRound = game.getRounds().size()-1;
        if(game.getRounds().get(currentRound).getId().equals(round.getId())){
            return true;
        }else{
            return false;
        }
    }

}
