package ch.uzh.ifi.seal.soprafs17.service.RuleEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.GetStoneMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.GiveCardToUserMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.SailShipMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erion on 03.04.17.
 */
@Service
@Component
public class RuleBook {

    private List<IRule> rules=new ArrayList<>();

    public RuleBook(){}

    @PostConstruct
    public void addRule(){
        this.rules.add(new AddStoneToShipRule());
        this.rules.add(new SailShipRule());
        this.rules.add(new GetStoneRule());
        this.rules.add(new GiveCardToUserRule());
        this.rules.add(new PlayMarketCardRule());
    }

    public synchronized void apply(Game game, AMove move) {
        for(IRule rule:rules){
            if(rule.supports(move)){
                    rule.apply(game, move);
            }
        }
    }

    public synchronized void applyRule(Game game, AMove move){
        this.apply(game,move);
    }

    public List<IRule> getRules() {
        return rules;
    }

    public void setRules(List<IRule> rules) {
        this.rules = rules;
    }

}
