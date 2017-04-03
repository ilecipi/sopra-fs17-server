package ch.uzh.ifi.seal.soprafs17.service.RuleEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erion on 03.04.17.
 */
@Service
@Transactional
@Controller
public class RuleBook {
    List<IRule> rules = new ArrayList<>();


    public void addRule(IRule rule){
        rules.add(rule);
    }

    public void apply(Game game, AMove move) {
        for(IRule rule:rules){
            if(rule.supports(move)){
                rule.apply(game,move);
            }
        }
    }

    public synchronized void applyRule(Game game, AMove move){
        this.apply(game,move);
    }
}
