package ua.com.owu.crm_programming_school.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.com.owu.crm_programming_school.models.ResponseError;

import java.io.IOException;
import java.util.*;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> exceptionHandler(MethodArgumentNotValidException e){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Error-Message", "Not Valid");
        ResponseEntity<String> response = new ResponseEntity<>(Objects.requireNonNull(e.getFieldError()).getDefaultMessage(),httpHeaders, HttpStatus.BAD_REQUEST);
        return response;
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> exceptionHandler(HttpMessageNotReadableException e) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Error-Message", "Not Readable");
        ResponseEntity<String> response = new ResponseEntity<>(Objects.requireNonNull(e.getLocalizedMessage()), httpHeaders, HttpStatus.BAD_REQUEST);
        return response;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> exceptionHandler(IllegalArgumentException e) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Error-Message", "Invalid id");
        ResponseEntity<String> response = new ResponseEntity<>(Objects.requireNonNull(e.getLocalizedMessage()), httpHeaders, HttpStatus.BAD_REQUEST);
        return response;
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> exceptionHandler(NoSuchElementException e) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Error-Message", "No such object exists");
        ResponseEntity<String> response = new ResponseEntity<>(Objects.requireNonNull(e.getLocalizedMessage()), httpHeaders, HttpStatus.BAD_REQUEST);
        return response;
    }

    @ExceptionHandler(ExpiredJwtException.class )
    public ResponseEntity<String> exceptionHandler(ExpiredJwtException  expiredJwtException, HttpServletResponse response) {

        response.setHeader("TokenError", "Invalid token");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        ResponseError responseError = ResponseError
                .builder()
                .error("Invalid token")
                .code(401)
                .build();
        try {
            response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(responseError));
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(AuthenticationException.class )
    public ResponseEntity<String> exceptionHandler(AuthenticationException authenticationException, HttpServletResponse response) {

        response.setHeader("AuthenticationError", "wrong email or password");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        ResponseError responseError = ResponseError
                .builder()
                .error("wrong email or password")
                .code(401)
                .build();
        try {
            response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(responseError));
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> exceptionHandler(ConstraintViolationException constraintViolationException, HttpServletResponse response) {

        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();

        List<String> errorMessages = new ArrayList<>();

        for (ConstraintViolation<?> violation : violations) {
            String errorMessage = violation.getPropertyPath() + ": " + violation.getMessage();
            errorMessages.add(errorMessage);
        }

        response.setHeader("ValidationError", "one of field is not valid");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        ResponseError responseError = ResponseError
                .builder()
                .error("One or more fields are not valid")
                .code(400)
                .details(errorMessages)
                .build();

        try {
            response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(responseError));
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}


