package ch.uzh.ifi.seal.soprafs17.service.validatorEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.GetStoneMove;
import ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception.*;

/**
 * Created by erion on 05.04.17.
 */
public class GetStoneValidator implements IValidator {

    public boolean supports(AMove amove) {
        return amove instanceof GetStoneMove;
    }

    @Override
    public void validate(Game game, AMove amove) throws ValidationException {
        if (supports(amove)) {
            GetStoneMove castedMove = (GetStoneMove) amove;
            if (!BasicValidation.checkCurrentUser(game, amove.getUser())) {
                throw new NotCurrentPlayerException();
            }
            if (!BasicValidation.checkCurrentRound(game, amove.getRound())) {
                throw new NotCurrentRoundException();
            }
            if (castedMove.getRound() == null) {
                throw new NotCurrentRoundException();
            }
            if (castedMove.getUser().getSupplySled() == 5) {
                throw new MaxNumberOfStonesReachedException();
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
            if (castedMove.getRound().getIsActionCardSail() != 0) {
                throw new SailCardIsBeingPlayedException();
            }
            if (castedMove.getRound().getIsActionCardChisel() != 0) {
                throw new ChiselCardIsBeingPlayedException();
            }
            if (castedMove.getRound().isActionCardLever()) {
                throw new LeverCardIsBeingPlayedException();
            }
        }
    }
}
