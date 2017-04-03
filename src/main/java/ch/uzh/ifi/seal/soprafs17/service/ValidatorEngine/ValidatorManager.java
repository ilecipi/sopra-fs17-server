package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilecipi on 03.04.17.
 */
@Service
public class ValidatorManager {

     List<IValidator> validators = new ArrayList<>();

        public void addValidator(IValidator iValidator) {
            this.validators.add(iValidator);
        }

        public void validate(Game game, AMove amove){
            this.validateSync(game,amove);
        }

        public synchronized boolean validateSync(Game game, AMove amove){
            for(IValidator validator : validators){
                if(validator.supports(amove)){
                    if(validator.validate(game,amove)){
                        return true;
                    }
                }
            }
            return false;
        }
}

