package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception.*;
import org.springframework.stereotype.Service;

/**
 * Created by liwitz on 04.04.17.
 */
@Service
public class SailShipValidator implements IValidator {
    @Override
    public boolean supports(AMove amove) {
        return amove instanceof SailShipMove;
    }

    @Override
    public void validate(Game game, AMove amove) {
        if(supports(amove)){
            SailShipMove castedMove = (SailShipMove)amove;
            /*
            *TODO: Add exceptions instead of returning false
            */
            if(!BasicValidation.checkCurrentUser(game,amove.getUser())){
                throw new NotCurrentPlayerException();
            }
            if(!BasicValidation.checkCurrentRound(game,amove.getRound())){
                throw new NotCurrentRoundException();
            }
            if(castedMove.getShip().isDocked()){
                throw new DockedShipException();
            }
            if(!castedMove.getShip().isReady()) {
                throw new NotReadyShipException();
            }
            if(castedMove.getSiteBoard().isOccupied()){
                throw new SiteBoardIsOccupiedException();
            }
            if(castedMove.getUser().getSupplySled() == 0){
                throw new NotEnoughStoneException();
            }

        }
    }
}
