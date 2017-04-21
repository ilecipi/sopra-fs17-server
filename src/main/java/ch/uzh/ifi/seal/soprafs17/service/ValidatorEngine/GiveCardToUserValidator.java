package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AddStoneToShipMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.GiveCardToUserMove;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception.*;

/**
 * Created by ilecipi on 13.04.17.
 */
public class GiveCardToUserValidator implements IValidator {
    @Override
    public boolean supports(AMove amove) {
        return amove instanceof GiveCardToUserMove;
    }

    @Override
    public void validate(Game game, AMove amove) throws ValidationException {
        if(supports(amove)){
            GiveCardToUserMove castedMove = (GiveCardToUserMove)amove;
            if(castedMove.getGame().getMarket().getUserColor().size()==0){
                throw new NotMoreUsersAvailableException();
            }
            if (!castedMove.getGame().getMarket().getUserColor().get(0).equals(castedMove.getUser().getColor())){
                throw new NotCurrentPlayerException();
            }
            if(castedMove.getGame().getMarket().getMarketCards().get(castedMove.getPosition()).isTaken()){
                throw new MarketCardAlreadyTaken();
            }
            if(castedMove.getRound().isImmediateCard()){
                System.out.println("In validator GiveCard: " + castedMove.getRound().isImmediateCard());
                throw new ImmediateCardNotPlayedException();
            }

        }
    }
}
