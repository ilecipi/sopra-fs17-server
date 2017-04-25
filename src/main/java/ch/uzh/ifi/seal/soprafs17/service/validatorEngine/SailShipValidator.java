package ch.uzh.ifi.seal.soprafs17.service.validatorEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception.*;
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
        if (supports(amove)) {
            SailShipMove castedMove = (SailShipMove) amove;
            if (!BasicValidation.checkCurrentUser(game, amove.getUser())) {
                throw new NotCurrentPlayerException();
            }
            if (!BasicValidation.checkCurrentRound(game, amove.getRound())) {
                throw new NotCurrentRoundException();
            }
            if (castedMove.getShip().isDocked()) {
                throw new DockedShipException();
            }
            if (!castedMove.getShip().isReady()) {
                throw new NotReadyShipException();
            }
            if (castedMove.getSiteBoard().isOccupied()) {
                throw new SiteBoardIsOccupiedException();
            }
            if (castedMove.getGame().getMarket().getUserColor().size() != 0) {
                throw new MarketCardsNotTaken();
            }
            if (castedMove.getRound().isImmediateCard()) {
                throw new ImmediateCardNotPlayedException();
            }
            if (castedMove.getRound().isActionCardHammer()) {
                throw new HammerCardIsBeingPlayedException();
            }
            if (castedMove.getRound().getIsActionCardSail() == 2) {
                throw new SailCardIsBeingPlayedException();
            }
            if (castedMove.getRound().getIsActionCardChisel() != 0) {
                throw new ChiselCardIsBeingPlayedException();
            }

        }
    }
}
