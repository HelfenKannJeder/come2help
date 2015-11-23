package de.helfenkannjeder.come2help.server.rest.exceptionhandling;

import de.helfenkannjeder.come2help.server.service.exception.DataError;
import org.springframework.validation.FieldError;

public class ClientError {

    public static ClientError fromFieldError(FieldError fieldError) {
        ClientError clientError = new ClientError();
        clientError.path = fieldError.getField();
        clientError.code = fieldError.getDefaultMessage();
        clientError.value = fieldError.getRejectedValue();

        return clientError;
    }

    public static ClientError fromDataError(DataError dataError) {
        ClientError clientError = new ClientError();
        clientError.code = dataError.code;
        clientError.value = dataError.value;

        return clientError;
    }

    public String path;
    public String code;
    public Object value;
}
