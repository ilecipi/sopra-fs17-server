package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tonio99tv on 04/04/17.
 */

@EnableWebMvc
@ResponseStatus(value= HttpStatus.FORBIDDEN,reason="Ship is not ready to be sailed")
public class NotReadyShipException extends ValidationException {

    public NotReadyShipException() {
        super("NotReadyShipException");
        Logger logger = Logger.getLogger(getClass().getName());
        logger.log(Level.SEVERE, "some message");
    }

}
