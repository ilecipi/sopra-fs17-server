package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AddStoneToShipMove;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception.NotCurrentPlayerException;
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
    public void validate(Game game, AMove amove) {
        if(supports(amove)){
            AddStoneToShipMove castedMove = (AddStoneToShipMove)amove;
            /*
            *TODO: Add exceptions instead of returning false
            */
            if(!BasicValidation.checkCurrentUser(game,amove.getUser())){
                throw new NotCurrentPlayerException();
            }
            if(!BasicValidation.checkCurrentRound(game,amove.getRound())){
            }
            if(castedMove.getShip().getStones()[castedMove.getPosition()]!=null) {
            }
        }
    }
}
