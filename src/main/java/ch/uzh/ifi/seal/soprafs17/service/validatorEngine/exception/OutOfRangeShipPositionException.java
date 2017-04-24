package ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception;

import java.util.logging.Logger;

/**
 * Created by ilecipi on 04.04.17.
 */
public class OutOfRangeShipPositionException extends ValidationException {
    public Logger logger;
    public OutOfRangeShipPositionException(){
        super("UnavailableShipPlaceException");
        this.logger = Logger.getLogger(getClass().getName());
    }
}
