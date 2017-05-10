package ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.logging.Logger;

/**
 * Created by tonio99tv on 04/04/17.
 */

public class CardAlreadyPlayedException extends ValidationException{

    private Logger logger;

    public CardAlreadyPlayedException() {
        super("CardAlreadyPlayedException");
        this.logger = Logger.getLogger(getClass().getName());
    }


}
