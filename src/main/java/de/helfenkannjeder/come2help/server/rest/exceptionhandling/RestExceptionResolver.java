package de.helfenkannjeder.come2help.server.rest.exceptionhandling;

import com.fasterxml.jackson.core.JsonParseException;
import de.helfenkannjeder.come2help.server.service.exception.DuplicateResourceException;
import de.helfenkannjeder.come2help.server.service.exception.InvalidDataException;
import de.helfenkannjeder.come2help.server.service.exception.ResourceNotFoundException;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionResolver {

    private final Logger logger = LoggerFactory.getLogger(RestExceptionResolver.class);

    @Autowired
    private MappingJackson2HttpMessageConverter jsonMessageConverter;

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
        return resolveException(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resolveResourceNotFoundException(HttpServletRequest request, ResourceNotFoundException ex) {
        return LoggableErrorResponseCreator.create(HttpStatus.NOT_FOUND)
                .withDescription(ex.getMessage())
                .createErrorResponse();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> resolveHttpMediaTypeNotSupportedException(HttpServletRequest request, HttpMediaTypeNotSupportedException ex) {
        return resolveException(ex, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
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
        return resolveException(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> resolveHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        return resolveException(ex, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ErrorResponse> resolveException(Exception ex, HttpStatus status) {
        return LoggableErrorResponseCreator.create(status)
                .withDescription(ex.getMessage())
                .createErrorResponse();
    }

    public void resolveException(Exception ex, HttpStatus status, HttpServletResponse response) {
        ResponseEntity<ErrorResponse> responseEntity = resolveException(ex, status);

        try {
            response.setStatus(responseEntity.getStatusCode().value());
            jsonMessageConverter.write(responseEntity.getBody(), MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
