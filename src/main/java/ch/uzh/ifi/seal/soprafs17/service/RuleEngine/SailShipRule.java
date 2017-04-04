package ch.uzh.ifi.seal.soprafs17.service.RuleEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.model.repository.*;
import ch.uzh.ifi.seal.soprafs17.service.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by liwitz on 04.04.17.
 */

@Service
@Transactional
public class SailShipRule extends RuleBook implements IRule {

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