package ch.uzh.ifi.seal.soprafs17.service.validatorEngine;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AddStoneToShipMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception.*;
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
    public void validate(Game game, AMove amove) throws ValidationException {
        if(supports(amove)){
            AddStoneToShipMove castedMove = (AddStoneToShipMove)amove;
            if(castedMove.getGame().getStatus()!= GameStatus.RUNNING){
                throw new GameFinishedException();
            }
            if(!BasicValidation.checkCurrentUser(game,amove.getUser())){
                throw new NotCurrentPlayerException();
            }
            if(!BasicValidation.checkCurrentRound(game,amove.getRound())){
                throw new NotCurrentRoundException();
            }
            if(castedMove.getUser().getSupplySled() < 1){
                throw new NotEnoughStoneException();
            }
            if(castedMove.getRound()==null){
                throw new NotCurrentRoundException();
            }
            if(castedMove.getShip().getStones()[castedMove.getPosition()]!=null) {
                throw new UnavailableShipPlaceException();
            }
            if(castedMove.getShip().getMaxStones()-1<castedMove.getPosition()){
                throw new OutOfRangeShipPositionException();
            }
            if(castedMove.getGame().getMarket().getUserColor().size()!=0){
                throw new MarketCardsNotTaken();
            }
            if(castedMove.getRound().isImmediateCard()){
                throw new ImmediateCardNotPlayedException();
            }
            if(castedMove.getRound().getIsActionCardSail()==2){
                boolean noShipsReady = true;
                for(AShip s : castedMove.getRound().getShips()){
                    if(s.isReady()&&!s.isDocked()){
                        noShipsReady = false;
                    }
                }
                if(castedMove.getShip().getMinStones()>castedMove.getShip().getAddedStones()+1&&noShipsReady){
                    throw new ShipWontBeReadyException();
                }
            }
            if(castedMove.getRound().getIsActionCardSail()==1){
                throw new SailCardIsBeingPlayedException();
            }
            if(castedMove.getRound().isActionCardLever()){
                throw new LeverCardIsBeingPlayedException();
            }
        }
    }
}
