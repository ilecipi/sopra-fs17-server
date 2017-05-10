package ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.logging.Logger;

/**
 * Created by erion on 10.05.17.
 */
@EnableWebMvc
@ResponseStatus(value= HttpStatus.FORBIDDEN,reason="CardAlreadyPlayedException")
public class EmptyStoneQuarryException extends ValidationException{

    private Logger logger;

    public EmptyStoneQuarryException() {
        super("EmptyStoneQuarryException");
        this.logger = Logger.getLogger(getClass().getName());
    }


}