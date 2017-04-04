package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ilecipi on 04.04.17.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND,reason="Not Current Player")
public class NotCurrentPlayerException extends ValidationException{

    private Logger logger;

    public NotCurrentPlayerException() {
        super("NotCurrentPlayerException");
        this.logger = Logger.getLogger(getClass().getName());
        throw new RuntimeException("NotCurrentPlayerException");
    }

}
