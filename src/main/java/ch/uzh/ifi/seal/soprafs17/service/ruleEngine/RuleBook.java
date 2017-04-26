package ch.uzh.ifi.seal.soprafs17.service.ruleEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
        this.rules.add(new PlayLeverCardRule());
    }

    public synchronized void apply(Game game, AMove move) {
        for(IRule rule:rules){
            if(rule.supports(move)){
                    rule.apply(game, move);
            }
        }
    }

//    public synchronized void applyRule(Game game, AMove move){
//        this.apply(game,move);
//    }

    public List<IRule> getRules() {
        return rules;
    }

    public void setRules(List<IRule> rules) {
        this.rules = rules;
    }

}
