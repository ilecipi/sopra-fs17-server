package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AddStoneToShipMove;
import org.springframework.stereotype.Service;

/**
 * Created by ilecipi on 03.04.17.
 */
@Service
public class AddStoneToShipValidator implements IValidator {
    @Override
    public boolean supports(AMove amove) {
        return amove instanceof AddStoneToShipMove;
    }

    @Override
    public boolean validate(Game game, AMove amove) {
        if(supports(amove)){
            AddStoneToShipMove castedMove = (AddStoneToShipMove)amove;
            /*
            *TODO: Add exceptions instead of returning false
            */
            if(!BasicValidation.checkCurrentUser(game,amove.getUser())){
                return false;
            }
            if(!BasicValidation.checkCurrentRound(game,amove.getRound())){
                return false;
            }
            if(castedMove.getShip().getStones()[castedMove.getPosition()]!=null) {
                return false;
            }
            return true;
        }
        return false;
    }
}
