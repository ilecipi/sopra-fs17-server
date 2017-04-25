package ch.uzh.ifi.seal.soprafs17.service.ruleEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AddStoneToShipMove;
import org.springframework.stereotype.Service;

/**
 * Created by erion on 03.04.17.
 */
@Service
public class AddStoneToShipRule implements IRule {

    
    public boolean supports(AMove move){
        return move instanceof AddStoneToShipMove;
    }

    @Override
    public void apply(Game game, AMove move) {
        if(supports(move)) {
            Round round = move.getRound();
            AddStoneToShipMove castedMove = (AddStoneToShipMove) move;
            castedMove.makeMove(game);
            round.getAMoves().add(move);
        }
    }

}
