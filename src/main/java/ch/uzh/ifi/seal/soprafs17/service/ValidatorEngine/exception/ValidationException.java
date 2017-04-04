package ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by ilecipi on 04.04.17.
 */
@EnableWebMvc
@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "VALIDATION EXCEPTION")
public class ValidationException extends RuntimeException {
        String message;

        public ValidationException(String message){
            this.message=message;
        }

        public String getMessage(){
            return this.message;
        }

}
