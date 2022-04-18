package tech.kerok.portfolio.rps.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PlayerAlreadyInGameAdvice {

    @ResponseBody
    @ExceptionHandler(PlayerAlreadyInGameException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    String playerAlreadyInGameHandler(PlayerAlreadyInGameException e) {
        return e.getMessage();
    }
}
