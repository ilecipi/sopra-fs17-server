package ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception;

import java.util.logging.Logger;

/**
 * Created by ilecipi on 04.04.17.
 */
public class NotCurrentRoundException extends ValidationException {
    private Logger logger;

    public NotCurrentRoundException() {
        super("NotCurrentRoundException");
        this.logger = Logger.getLogger(getClass().getName());
    }
}
