package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.Hammer;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.MCAction;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.PlayMarketCardMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception.*;
import org.springframework.stereotype.Service;

import javax.smartcardio.Card;


@Service
public class PlayMarketCardValidator implements IValidator {
    @Override
    public boolean supports(AMove amove) {
        return amove instanceof PlayMarketCardMove;
    }

    @Override
    public void validate(Game game, AMove amove) {
        if(supports(amove)){
            PlayMarketCardMove castedMove = (PlayMarketCardMove) amove;
            boolean userOwnsCard = false;
            for(AMarketCard m : castedMove.getUser().getMarketCards()){
                if(m.getId()==((PlayMarketCardMove)amove).getaMarketCard().getId()){
                    userOwnsCard = true;
                }
            }
            if(!userOwnsCard){
                throw new NotYourCardException();
            }
            if(((PlayMarketCardMove)amove).getaMarketCard().isPlayed()){
                throw  new CardAlreadyPlayedException();
            }
            if(castedMove.getRound().getId()!=game.getCurrentRound().getId()){
                    throw new NotCurrentRoundException();
            }
            if(castedMove.getaMarketCard() instanceof MCAction){
                if(castedMove.getGame().getMarket().getUserColor().size()!=0){
                    throw new MarketCardsNotTaken();
                }
                if(!castedMove.getUser().equals(castedMove.getGame().getCurrentPlayer())){
                    throw new NotCurrentPlayerException();
                }
                if(castedMove.getaMarketCard() instanceof Hammer) {
                    boolean freePosition = false;
                    for (AShip s : game.getCurrentRound().getShips()) {
                        if (s.getMaxStones() > s.getAddedStones()) {
                            freePosition = true;
                        }
                    }
                    if (!freePosition) {
                        throw new UserCanNotPlayThisCardException();
                    }
                }
            }
;        }
    }
}
