package ch.uzh.ifi.seal.soprafs17.service.ruleEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.SailShipMove;
import org.springframework.stereotype.Service;

/**
 * Created by liwitz on 04.04.17.
 */

@Service
public class SailShipRule implements IRule {

    public boolean supports(AMove move){
        return move instanceof SailShipMove;
    }

    @Override
    public void apply(Game game, AMove move) {
        if(supports(move)){
            Round round = move.getRound();
            SailShipMove castedMove = (SailShipMove)move;
            castedMove.makeMove(game);
            round.getAMoves().add(move);
        }
    }

}