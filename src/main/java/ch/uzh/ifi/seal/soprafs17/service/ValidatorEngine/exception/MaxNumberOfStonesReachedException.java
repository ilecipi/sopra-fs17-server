package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception;

import java.util.logging.Logger;

/**
 * Created by erion on 17.04.17.
 */
public class MaxNumberOfStonesReachedException extends ValidationException{
    private Logger logger;

    public MaxNumberOfStonesReachedException() {
        super("MaxNumberOfStonesReachedException");
        this.logger = Logger.getLogger(getClass().getName());
    }
}
