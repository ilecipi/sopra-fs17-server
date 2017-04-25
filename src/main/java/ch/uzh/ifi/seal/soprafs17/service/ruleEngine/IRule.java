package ch.uzh.ifi.seal.soprafs17.service.ruleEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import org.springframework.stereotype.Service;

/**
 * Created by erion on 03.04.17.
 */
@Service
public interface IRule {
    boolean supports(AMove move);

    void apply(Game game, AMove move);
}
