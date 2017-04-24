package ch.uzh.ifi.seal.soprafs17.service.RuleEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.GetStoneMove;
import org.hibernate.annotations.SourceType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by erion on 05.04.17.
 */
@Service
public class GetStoneRule implements IRule{


    public boolean supports(AMove move) {
        return move instanceof GetStoneMove;
    }

    @Override
    public void apply(Game game, AMove move) {
        if(supports(move)){
            Round round = move.getRound();
            GetStoneMove castedMove = (GetStoneMove) move;
            castedMove.makeMove(game);
            round.getAMoves().add(move);
        }
    }
}
