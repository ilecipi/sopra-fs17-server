package ch.uzh.ifi.seal.soprafs17.web.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception.NotCurrentPlayerException;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class GenericResource {

	Logger logger = LoggerFactory.getLogger(GenericResource.class);

	@ExceptionHandler(TransactionSystemException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public void handleTransactionSystemException(Exception exception, HttpServletRequest request) {
		logger.error("", exception);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleException(RuntimeException exception, HttpServletRequest request) {
		logger.error("", exception);
	}

	@ExceptionHandler(ValidationException.class)
	public void validationExceptionHandler(ValidationException exception, HttpServletResponse response) {
//		if(exception instanceof NotCurrentPlayerException){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//		}
		logger.error("", exception);
	}
}
