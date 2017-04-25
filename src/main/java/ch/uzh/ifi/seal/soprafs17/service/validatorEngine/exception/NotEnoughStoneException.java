package ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception;

import java.util.logging.Logger;

/**
 * Created by erion on 17.04.17.
 */
public class NotEnoughStoneException extends ValidationException {
    private Logger logger;

    public NotEnoughStoneException() {
        super("NotEnoughStoneException");
        this.logger = Logger.getLogger(getClass().getName());
    }
}
