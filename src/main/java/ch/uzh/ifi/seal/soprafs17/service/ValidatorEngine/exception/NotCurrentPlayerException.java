package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ilecipi on 04.04.17.
 */
@EnableWebMvc
@ResponseStatus(value= HttpStatus.FORBIDDEN,reason="Not Current Player")
public class NotCurrentPlayerException extends ValidationException {

    private Logger logger;

    public NotCurrentPlayerException() {
        super("NotCurrentPlayerException");
        this.logger = Logger.getLogger(getClass().getName());
//        throw new RuntimeException("NotCurrentPlayerException");
    }


}
