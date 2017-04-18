package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.GetStoneMove;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.AddStoneToShipRule;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.GetStoneRule;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.GiveCardToUserRule;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.SailShipRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilecipi on 03.04.17.
 */
@Service
public class ValidatorManager {

    private List<IValidator> validators=new ArrayList<>();

    @PostConstruct
    public void addValidator(){
        this.validators.add(new AddStoneToShipValidator());
        this.validators.add(new GetStoneValidator());
        this.validators.add(new SailShipValidator());
    }
    public ValidatorManager(){}

    public void validate(Game game, AMove amove){
            this.validateSync(game,amove);
    }

    public synchronized void validateSync(Game game, AMove amove){
            for(IValidator validator : validators){
                if(validator.supports(amove)&& amove instanceof GetStoneMove){
                    validator.validate(game,amove);
                }else if(validator.supports(amove)){
                    validator.validate(game,amove);
                }
            }
    }

    public List<IValidator> getValidators() {
        return validators;
    }

    public void setValidators(List<IValidator> validators) {
        this.validators = validators;
    }

}

