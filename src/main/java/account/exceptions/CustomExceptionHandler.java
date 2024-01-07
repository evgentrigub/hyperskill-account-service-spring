package account.exceptions;

import account.models.ErrorDetails;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();

        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);

            int statusCode = HttpStatus.BAD_REQUEST.value();
            String reason = HttpStatus.BAD_REQUEST.getReasonPhrase();
            String errorMessage = fieldError.getDefaultMessage();
            String uri = ((ServletWebRequest) request).getRequest().getRequestURI();

            ErrorDetails errorDetails = new ErrorDetails(
                    LocalDateTime.now(),
                    statusCode,
                    reason,
                    errorMessage,
                    uri
            );

            return ResponseEntity.status(ex.getStatusCode()).body(errorDetails);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> handle(ConstraintViolationException ex, WebRequest request) {
        List<String> errorMessages = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        String errorMessage = String.join("; ", errorMessages);
        String uri = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorMessage,
                uri
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public void handleConflict(HttpServletResponse response) throws IOException {
        response.sendError(403, "Access denied!");
    }

}