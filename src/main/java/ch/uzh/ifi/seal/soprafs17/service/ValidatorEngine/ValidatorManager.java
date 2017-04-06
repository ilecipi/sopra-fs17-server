package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.GetStoneMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilecipi on 03.04.17.
 */
@Service
public class ValidatorManager {

    private List<IValidator> validators=new ArrayList<>();
    public ValidatorManager(){
    }

        public void addValidator(IValidator iValidator) {
            boolean hasToBeAdded=true;
            for(IValidator iV: validators){
                if(iV.getClass().equals(iValidator.getClass())){
                    hasToBeAdded=false;
                }
            }
            if(hasToBeAdded){
                validators.add(iValidator);
            }
        }

        public void validate(Game game, AMove amove){
            this.validateSync(game,amove);
        }

        public synchronized void validateSync(Game game, AMove amove){
            for(IValidator validator : validators){
                if(validator.supports(amove)&& amove instanceof GetStoneMove &&game.getCurrentPlayer().getSupplySled()>=0){
                    validator.validate(game,amove);
                }else if(validator.supports(amove)&&game.getCurrentPlayer().getSupplySled()>=1){
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

