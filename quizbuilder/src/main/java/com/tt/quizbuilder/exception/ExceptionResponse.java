package com.tt.quizbuilder.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExceptionResponse {

    private String message;
    private LocalDateTime dateTime;

    private List<String> violations;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<String> getViolations() {
        if(violations== null) {
            violations = new ArrayList<>();
        }

        return violations;
    }

    public void setViolations(List<String> violations) {
        this.violations = violations;
    }
}
