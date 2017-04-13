package ch.uzh.ifi.seal.soprafs17.service.RuleEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AddStoneToShipMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.GiveCardToUserMove;

/**
 * Created by ilecipi on 13.04.17.
 */
public class GiveCardToUserRule extends RuleBook implements IRule {

    public boolean supports(AMove move){
        return move instanceof GiveCardToUserMove;
    }

    @Override
    public void apply(Game game, AMove move) {
        if(supports(move)){
            Round round = move.getRound();
            GiveCardToUserMove castedMove = (GiveCardToUserMove) move;
            castedMove.makeMove(game);
            round.getAMoves().add(move);
        }
    }
}
