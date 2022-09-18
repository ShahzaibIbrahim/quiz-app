package com.tt.quizbuilder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(ProcessException.class)
        public ResponseEntity<Object> handleProcessExceptions(ProcessException exception, WebRequest webRequest) {
            ExceptionResponse response = new ExceptionResponse();
            response.setDateTime(LocalDateTime.now());
            response.setMessage(exception.getMessage());
            ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            return entity;
        }


        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<Object> handleConstraintExceptions(ConstraintViolationException exception, WebRequest webRequest) {
            ExceptionResponse response = new ExceptionResponse();
            response.setDateTime(LocalDateTime.now());
            exception.getConstraintViolations().forEach(x -> response.getViolations().add(x.getMessage()));
            response.setMessage("Please correct all constraint errors");
            ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            return entity;
        }
}
