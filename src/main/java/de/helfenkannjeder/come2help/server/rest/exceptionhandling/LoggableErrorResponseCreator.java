package de.helfenkannjeder.come2help.server.rest.exceptionhandling;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LoggableErrorResponseCreator {

    public static LoggableErrorResponseCreator create(HttpStatus httpStatus) {
        return new LoggableErrorResponseCreator(httpStatus);
    }

    private ErrorResponse errorResponse;
    private HttpStatus httpStatus;

    public LoggableErrorResponseCreator(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        errorResponse = new ErrorResponse();
        errorResponse.incidentId = generateIncidentId();
    }

    public LoggableErrorResponseCreator withClientErrors(List<ClientError> clientErrors) {
        errorResponse.clientErrors = clientErrors;

        return this;
    }

    public LoggableErrorResponseCreator withDescription(String description) {
        errorResponse.description = description;

        return this;
    }

    public LoggableErrorResponseCreator logClientError(Logger logger) {

        return this;
    }

    public LoggableErrorResponseCreator logException(Logger logger, Exception ex) {
        logger.error(ex.toString(), ex);
        return this;
    }

    public ResponseEntity<ErrorResponse> createErrorResponse() {
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    private String generateIncidentId() {
        return String.valueOf(UUID.randomUUID());
    }
}
