package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by erion on 03.04.17.
 */
@Transactional
@Controller
public interface IRule {
    void apply (Game game, AMove move);
    boolean supports(AMove move);
}
