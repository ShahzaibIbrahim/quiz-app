package com.tt.quizbuilder.exception;

import com.tt.quizbuilder.util.ResponseConstants;
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
            GlobalResponse response = new GlobalResponse();
            response.setMessage(exception.getMessage());
            response.setResponseCode(ResponseConstants.FAILURE_CODE);
            ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.OK);
            return entity;
        }


        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<Object> handleConstraintExceptions(ConstraintViolationException exception, WebRequest webRequest) {
            GlobalResponse response = new GlobalResponse();
            response.setResponseCode(ResponseConstants.FAILURE_CODE); // WILL CHANGE IT TO SOMETHING ELSE IF WE WANT TO SHOW VIOLATIONS ON FRONTEND
            exception.getConstraintViolations().forEach(x -> response.getViolations().add(x.getMessage()));
            response.setMessage("Please correct all constraint errors");
            ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.OK);
            return entity;
        }
        @ExceptionHandler(Exception.class)
        public ResponseEntity<Object> handleExceptions(Exception exception, WebRequest webRequest) {
            exception.printStackTrace();
            GlobalResponse response = new GlobalResponse();
            response.setMessage("Something went wrong! Please contact administrator");
            ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            return entity;
        }
}
