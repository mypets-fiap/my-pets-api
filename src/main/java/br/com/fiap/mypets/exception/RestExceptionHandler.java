package br.com.fiap.mypets.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDetails> BadRequestException(BadRequestException bre){
        List<String> errors = new ArrayList<>();

        errors.add(bre.getMessage());

        return new ResponseEntity<>(
                new ExceptionDetails(
                        "Bad Request",
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now(),
                        errors
                )
                , HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionDetails> UnauthorizedException(UnauthorizedException bre){
        List<String> errors = new ArrayList<>();

        errors.add(bre.getMessage());

        return new ResponseEntity<>(
                new ExceptionDetails(
                        "Unauthorized",
                        HttpStatus.UNAUTHORIZED.value(),
                        LocalDateTime.now(),
                        errors
                )
                , HttpStatus.UNAUTHORIZED
        );
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> erros = new ArrayList<String>();

        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            erros.add(erro.getDefaultMessage());
        }

        return new ResponseEntity<>(
                new ExceptionDetails(
                        "Bad Request",
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now(),
                        erros
                ), HttpStatus.BAD_REQUEST
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        ExceptionDetails exceptionDetails = new ExceptionDetails(
                ex.getCause().getMessage(),
                statusCode.value(),
                LocalDateTime.now(),
                new ArrayList<>(Arrays.asList(ex.getMessage()))
        );
        return new ResponseEntity<>(exceptionDetails, headers, statusCode);
    }
}
