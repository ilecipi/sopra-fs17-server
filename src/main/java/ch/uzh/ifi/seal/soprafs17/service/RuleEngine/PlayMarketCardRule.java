package ch.uzh.ifi.seal.soprafs17.service.RuleEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AddStoneToShipMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.PlayMarketCardMove;
import org.springframework.stereotype.Service;

/**
 * Created by erion on 03.04.17.
 */
@Service
public class PlayMarketCardRule extends RuleBook implements IRule {

    
    public boolean supports(AMove move){
        return move instanceof PlayMarketCardMove;
    }

    @Override
    public void apply(Game game, AMove move) {
        if(supports(move)) {
            Round round = move.getRound();
            PlayMarketCardMove castedMove = (PlayMarketCardMove) move;
            castedMove.makeMove(game);
            round.getAMoves().add(move);
        }
    }

}
