package hexlet.code.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ResponseBody
@ControllerAdvice
public final class HandlerException {

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> generalExceptionHandler(Exception exception) {
        return new ResponseEntity<>("validation error: " + exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
