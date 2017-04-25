package ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception;

import java.util.logging.Logger;

/**
 * Created by ilecipi on 04.04.17.
 */
public class NotCurrentPlayerException extends ValidationException {

    private Logger logger;

    public NotCurrentPlayerException() {
        super("NotCurrentPlayerException");
        this.logger = Logger.getLogger(getClass().getName());
    }


}
