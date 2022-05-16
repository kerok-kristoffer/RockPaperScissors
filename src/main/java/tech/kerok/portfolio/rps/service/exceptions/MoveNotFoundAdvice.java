package tech.kerok.portfolio.rps.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MoveNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(MoveNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String moveNotFoundHandler(MoveNotFoundException e) {
        return e.getMessage();
    }
}
