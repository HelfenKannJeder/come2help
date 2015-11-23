package de.helfenkannjeder.come2help.server.rest.exceptionhandling;

import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import de.helfenkannjeder.come2help.server.service.exception.DuplicateResourceException;
import de.helfenkannjeder.come2help.server.service.exception.InvalidDataException;
import de.helfenkannjeder.come2help.server.service.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionResolver {

    private final Logger logger = LoggerFactory.getLogger(RestExceptionResolver.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> resolveException(Exception ex) {
        return LoggableErrorResponseCreator.create(HttpStatus.INTERNAL_SERVER_ERROR)
                .withDescription(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .logException(logger, ex)
                .createErrorResponse();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return LoggableErrorResponseCreator.create(HttpStatus.BAD_REQUEST)
                .withClientErrors(ex.getBindingResult().getFieldErrors().stream().map(ClientError::fromFieldError).collect(Collectors.toList()))
                .createErrorResponse();
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> resolveInvalidDataException(InvalidDataException ex) {
        return LoggableErrorResponseCreator.create(HttpStatus.BAD_REQUEST)
                .withClientErrors(ex.getErrors().stream().map(ClientError::fromDataError).collect(Collectors.toList()))
                .createErrorResponse();
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> resolveDuplicateResourceException(HttpServletRequest request, DuplicateResourceException ex) {
        return LoggableErrorResponseCreator.create(HttpStatus.CONFLICT)
                .withDescription(ex.getMessage())
                .createErrorResponse();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resolveDuplicateResourceException(HttpServletRequest request, ResourceNotFoundException ex) {
        return LoggableErrorResponseCreator.create(HttpStatus.NOT_FOUND)
                .withDescription(ex.getMessage())
                .createErrorResponse();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> resolveHttpMediaTypeNotSupportedException(HttpServletRequest request, HttpMediaTypeNotSupportedException ex) {
        return LoggableErrorResponseCreator.create(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .withDescription(ex.getMessage())
                .createErrorResponse();
    }
}
