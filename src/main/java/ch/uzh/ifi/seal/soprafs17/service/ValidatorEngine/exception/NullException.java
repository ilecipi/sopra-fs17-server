package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception;

import java.util.logging.Logger;

/**
 * Created by ilecipi on 04.04.17.
 */
public class NullException extends ValidationException {
    public Logger logger;

    public NullException() {
        super("NullException");
        this.logger = Logger.getLogger(getClass().getName());
    }
}