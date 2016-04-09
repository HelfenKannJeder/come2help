package de.helfenkannjeder.come2help.server.rest.exceptionhandling;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.helfenkannjeder.come2help.server.configuration.ObjectMapperFactory;
import de.helfenkannjeder.come2help.server.rest.logging.LogSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LoggableErrorResponseCreator {

    private final ObjectMapper objectMapper = ObjectMapperFactory.objectMapperForRestEndpoint();

    public static LoggableErrorResponseCreator create(HttpStatus httpStatus) {
        return new LoggableErrorResponseCreator(httpStatus);
    }

    private ErrorResponse errorResponse;
    private HttpStatus httpStatus;

    private LoggableErrorResponseCreator(HttpStatus httpStatus) {
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

    public LoggableErrorResponseCreator log() {
        LogSession.log("incident-id", errorResponse.incidentId);
        if(errorResponse.description != null) {
            LogSession.log("error-description", errorResponse.description);
        }
        if(errorResponse.clientErrors != null) {
            LogSession.log("client-errors", serializeClientErrors());
        }

        return this;
    }

    public LoggableErrorResponseCreator log(Exception ex) {
        log();
        LogSession.setExceptionToLog(ex);

        return this;
    }

    private String serializeClientErrors() {
        try {
            return objectMapper.writeValueAsString(errorResponse.clientErrors);
        }
        catch (Exception ex) {
            LogSession.log("log-serialisation-error", ex.getMessage());
            return "";
        }
    }

    public ResponseEntity<ErrorResponse> createErrorResponse() {
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    private String generateIncidentId() {
        return String.valueOf(UUID.randomUUID());
    }
}
