package de.helfenkannjeder.come2help.server.rest.exceptionhandling;

import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonParseException;
import de.helfenkannjeder.come2help.server.service.exception.DuplicateResourceException;
import de.helfenkannjeder.come2help.server.service.exception.InvalidDataException;
import de.helfenkannjeder.come2help.server.service.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
    public ResponseEntity<ErrorResponse> resolveResourceNotFoundException(HttpServletRequest request, ResourceNotFoundException ex) {
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> resolveHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException ex) {
        StringBuilder description = new StringBuilder();
        description.append("Could not read document.");
        if (ex.getRootCause() instanceof JsonParseException) {
            description.append(" Json parse not possible.").append(ex.getRootCause().getMessage());
        } else if (ex.getMessage().startsWith("Required request body is missing")) {
            description.append(" Missing request body.");
        }
        return LoggableErrorResponseCreator.create(HttpStatus.BAD_REQUEST)
                .withDescription(description.toString())
                .createErrorResponse();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> resolveMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        return LoggableErrorResponseCreator.create(HttpStatus.BAD_REQUEST)
                .withDescription(ex.getMessage())
                .createErrorResponse();
    }
}
