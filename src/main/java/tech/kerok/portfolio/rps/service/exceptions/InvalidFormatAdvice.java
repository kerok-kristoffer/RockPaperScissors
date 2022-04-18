package tech.kerok.portfolio.rps.service.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidFormatAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidFormatHandler(InvalidFormatException e) {
        return "Invalid input, please follow formats in readme";
    }
    @ResponseBody
    @ExceptionHandler(InvalidPlayerFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidPlayerFormatHandler(InvalidPlayerFormatException e) {
        return "Invalid input, please provide name according to API instructions";
    }
}
