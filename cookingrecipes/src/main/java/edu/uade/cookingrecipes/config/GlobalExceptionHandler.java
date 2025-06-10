package edu.uade.cookingrecipes.config;

import edu.uade.cookingrecipes.dto.auth.UserSuggestionResponseDto;
import edu.uade.cookingrecipes.exceptions.EmailAlreadyInUseException;
import edu.uade.cookingrecipes.exceptions.EmailNotActivatedException;
import edu.uade.cookingrecipes.exceptions.UsernameAlreadyInUseException;
import jakarta.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final HttpStatus constraintViolationExceptionCode = HttpStatus.BAD_REQUEST;
    private final String constraintViolationExceptionMessage = "Validation Failed: API returned " + constraintViolationExceptionCode.toString() + " due to ---> ";

    private final HttpStatus noSuchElementExceptionCode = HttpStatus.BAD_REQUEST;
    private final String noSuchElementExceptionMessage = "Database Query Failed: API returned " + noSuchElementExceptionCode + " due to ---> ";

    private final HttpStatus dataIntegrityViolationExceptionCode = HttpStatus.BAD_REQUEST;
    private final String dataIntegrityViolationExceptionMessage = "Database Query Failed: API returned " + dataIntegrityViolationExceptionCode + " due to ---> ";

    private final HttpStatus methodArgumentNotValidExceptionCode = HttpStatus.BAD_REQUEST;
    private final String methodArgumentNotValidExceptionMessage = "Validation Failed: API returned " + methodArgumentNotValidExceptionCode + " due to ---> ";

    private final HttpStatus httpMessageNotReadableExceptionCode = HttpStatus.BAD_REQUEST;
    private final String httpMessageNotReadableExceptionMessage = "Deserialization Failed: API returned " + httpMessageNotReadableExceptionCode + " due to ---> ";

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException (ConstraintViolationException e) {
        logger.info(constraintViolationExceptionMessage + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<>(constraintViolationExceptionCode);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException (NoSuchElementException e) {
        logger.info(noSuchElementExceptionMessage + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<>(noSuchElementExceptionCode);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException (DataIntegrityViolationException e) {
        logger.info(dataIntegrityViolationExceptionMessage + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<>(dataIntegrityViolationExceptionCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException (MethodArgumentNotValidException e) {
        logger.info(methodArgumentNotValidExceptionMessage + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<>(methodArgumentNotValidExceptionCode);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException (HttpMessageNotReadableException e) {
        logger.info(httpMessageNotReadableExceptionMessage + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<>(httpMessageNotReadableExceptionCode);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<Void> handleEmailAlreadyInUseException (EmailAlreadyInUseException e) {
        return new ResponseEntity<Void>(HttpStatusCode.valueOf(401));
    }

    @ExceptionHandler(EmailNotActivatedException.class)
    public ResponseEntity<Void> handleEmailNotActivatedException (EmailNotActivatedException e) {
        return new ResponseEntity<Void>(HttpStatusCode.valueOf(403));
    }

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public ResponseEntity<UserSuggestionResponseDto> handleUsernameAlreadyInUseException (UsernameAlreadyInUseException e) {
        return new ResponseEntity<UserSuggestionResponseDto>(e.getUsernameSuggestions(), HttpStatusCode.valueOf(400));
    }
}