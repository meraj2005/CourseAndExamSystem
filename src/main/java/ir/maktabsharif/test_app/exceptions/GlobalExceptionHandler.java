package ir.maktabsharif.test_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(build(ex.getMessage(), HttpStatus.NOT_FOUND));
    }


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(build(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");


        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(build(message, HttpStatus.BAD_REQUEST));
    }


    private ErrorResponse build(String msg, HttpStatus status) {
        ErrorResponse er = new ErrorResponse();
        er.setMessage(msg);
        er.setStatus(status.value());
        er.setTimestamp(LocalDateTime.now());
        return er;
    }
}