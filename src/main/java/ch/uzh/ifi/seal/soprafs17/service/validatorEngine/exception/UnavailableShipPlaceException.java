package ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception;

import java.util.logging.Logger;

/**
 * Created by ilecipi on 04.04.17.
 */
public class UnavailableShipPlaceException extends ValidationException {

    public Logger logger;
    public UnavailableShipPlaceException(){
        super("UnavailableShipPlaceException");
        this.logger = Logger.getLogger(getClass().getName());
    }
}
