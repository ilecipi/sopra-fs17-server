package ch.uzh.ifi.seal.soprafs17.service.validatorEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.*;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.PlayMarketCardMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception.*;
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
            //If it is a Decoration card or a Statue it cannot be played
            if(castedMove.getaMarketCard() instanceof MCDecoration || castedMove.getaMarketCard() instanceof Statue){
                throw new CardNotPlayableException();
            }
            //Check conditions for Action cards
            if(castedMove.getaMarketCard() instanceof MCAction){
                if(castedMove.getGame().getMarket().getUserColor().size()!=0){
                    throw new MarketCardsNotTaken();
                }
                if(!castedMove.getUser().equals(castedMove.getGame().getCurrentPlayer())){
                    throw new NotCurrentPlayerException();
                }

                //Check conditions for Hammer card
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
                //Check conditions for Sail card
                if(castedMove.getaMarketCard() instanceof Sail){
                    if(castedMove.getUser().getSupplySled()==0){
                        throw new UserCanNotPlayThisCardException();
                    }
                    boolean shipNotReady = false;
                    boolean allShipFull = true;
                    for(AShip s : game.getCurrentRound().getShips()){
                        if(s.getMinStones()<=s.getAddedStones()+1&&!s.isDocked()){
                            shipNotReady = true;
                        }
                        if(s.getAddedStones()<s.getMaxStones()&&!s.isDocked()){
                            allShipFull = false;
                        }
                    }
                    if(!shipNotReady){
                        throw new UserCanNotPlayThisCardException();
                    }
                    if(allShipFull){
                        throw new UserCanNotPlayThisCardException();
                    }
                }
                //Check conditions for Chisel card
                if(castedMove.getaMarketCard() instanceof Chisel){
                    if(castedMove.getUser().getSupplySled()<2){
                        throw new UserCanNotPlayThisCardException();
                    }
                    int counterFreePositions = 0;
                    for(AShip s : castedMove.getRound().getShips()){
                        if(s.getMaxStones()!=s.getAddedStones()&&!s.isDocked()){
                            counterFreePositions += s.getMaxStones()-s.getAddedStones();
                        }
                    }
                    if(counterFreePositions<2){
                        throw new UserCanNotPlayThisCardException();
                    }
                }

                //Check conditions for Lever card
                if(castedMove.getaMarketCard() instanceof Lever){
                   boolean noShipReady = true;
                   for(AShip s : castedMove.getRound().getShips()){
                       if(s.isReady()&&!s.isDocked()){
                           noShipReady = false;
                       }
                   }
                   if(noShipReady){
                       throw new UserCanNotPlayThisCardException();
                   }
                }
                if(castedMove.getRound().isActionCardLever()){
                    throw new LeverCardIsBeingPlayedException();
                }
                if(castedMove.getRound().getIsActionCardSail()!=0){
                    throw new SailCardIsBeingPlayedException();
                }
                if(castedMove.getRound().getIsActionCardChisel()!=0){
                    throw new ChiselCardIsBeingPlayedException();
                }
                if(castedMove.getRound().getIsActionCardSail()!=0){
                    throw new SailCardIsBeingPlayedException();
                }
                if(castedMove.getRound().isActionCardHammer()){
                    throw new HammerCardIsBeingPlayedException();
                }
            }
;        }
    }
}
