package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception;

import java.util.logging.Logger;

/**
 * Created by ilecipi on 04.04.17.
 */
public class OutOfRangeShipPosition extends ValidationException {
    public Logger logger;
    public OutOfRangeShipPosition(){
        super("UnavailableShipPlaceException");
        this.logger = Logger.getLogger(getClass().getName());
    }
}
