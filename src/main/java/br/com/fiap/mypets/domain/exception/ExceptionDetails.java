package br.com.fiap.mypets.domain.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ExceptionDetails {
    private String title;
    private int status;
    private LocalDateTime timestamp;
    private List<String> errors;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public ExceptionDetails(String title, int status, LocalDateTime timestamp, List<String> errors) {
        this.title = title;
        this.status = status;
        this.timestamp = timestamp;
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ExceptionDetails{" +
                "title='" + title + '\'' +
                ", status=" + status +
                ", timestamp=" + timestamp +
                ", errors=" + errors +
                '}';
    }
}
