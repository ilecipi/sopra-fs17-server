package ch.uzh.ifi.seal.soprafs17.service.validatorEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
        this.validators.add(new GiveCardToUserValidator());
        this.validators.add(new PlayMarketCardValidator());
    }
    public ValidatorManager(){}

//    public void validate(Game game, AMove amove){
//            this.validateSync(game,amove);
//    }

    public synchronized void validate(Game game, AMove amove){
            for(IValidator validator : validators){
                if(validator.supports(amove)){
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

