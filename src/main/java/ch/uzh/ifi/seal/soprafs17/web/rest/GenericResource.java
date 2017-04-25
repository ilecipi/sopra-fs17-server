package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.service.validatorEngine.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

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

    }

    @ExceptionHandler(CardAlreadyPlayedException.class)
    @ResponseStatus(value = HttpStatus.IM_USED, reason = "This card has been already used, you cannot play it again")
    public void validationExceptionHandler(CardAlreadyPlayedException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(ChiselCardIsBeingPlayedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Chisel card in use")
    public void validationExceptionHandler(ChiselCardIsBeingPlayedException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(DockedShipException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "This ship is already docked")
    public void validationExceptionHandler(DockedShipException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(HammerCardIsBeingPlayedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Hammere card in user")
    public void validationExceptionHandler(HammerCardIsBeingPlayedException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(LeverCardIsBeingPlayedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Lever card in use")
    public void validationExceptionHandler(LeverCardIsBeingPlayedException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(MarketCardAlreadyTaken.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "This Marketcard has already been picked up")
    public void validationExceptionHandler(MarketCardAlreadyTaken e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(MarketCardsNotTaken.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Marketcard have not been picked up")
    public void validationExceptionHandler(MarketCardsNotTaken e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(MaxNumberOfStonesReachedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Your supply sled is already full")
    public void validationExceptionHandler(MaxNumberOfStonesReachedException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(NotCurrentPlayerException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You are not the current player")
    public void validationExceptionHandler(NotCurrentPlayerException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(NotCurrentRoundException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Not in the current round")
    public void validationExceptionHandler(NotCurrentRoundException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(NotEnoughStoneException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You do not have enough stones")
    public void validationExceptionHandler(NotEnoughStoneException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(NotMoreUsersAvailableException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Cards cannot be taken form the Market")
    public void validationExceptionHandler(NotMoreUsersAvailableException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(NotReadyShipException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "The ship is not ready to be sailed yet")
    public void validationExceptionHandler(NotReadyShipException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(NotYourCardException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "You can not play this card")
    public void validationExceptionHandler(NotYourCardException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(NullException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Something went wrong")
    public void validationExceptionHandler(NullException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(OutOfRangeShipPositionException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Invalid position chosen")
    public void validationExceptionHandler(OutOfRangeShipPositionException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(SailCardIsBeingPlayedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Sail card in use")
    public void validationExceptionHandler(SailCardIsBeingPlayedException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(ShipWontBeReadyException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You cannot play this card, a ship will not be ready to be sailed")
    public void validationExceptionHandler(ShipWontBeReadyException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(SiteBoardIsOccupiedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "This siteboard is already occupied")
    public void validationExceptionHandler(SiteBoardIsOccupiedException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(UnavailableShipPlaceException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You can not place a stone in this position")
    public void validationExceptionHandler(UnavailableShipPlaceException e) {
        logger.error(e.getMessage());
    }

    @ExceptionHandler(UserCanNotPlayThisCardException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "The conditions for playing this card are not satisfied")
    public void validationExceptionHandler(UserCanNotPlayThisCardException e) {
        logger.error(e.getMessage());
    }


    //LASTONE IN THE LIST
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Unexpected error from the server")
    public void validationExceptionHandler(ValidationException e) {
        logger.error("", e);
    }


}
