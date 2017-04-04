package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception;

/**
 * Created by ilecipi on 04.04.17.
 */
public class ValidationException extends RuntimeException {
        String message;

        ValidationException(String message){
            this.message=message;
        }

        public String getMessage(){
            return this.message;
        }

}
