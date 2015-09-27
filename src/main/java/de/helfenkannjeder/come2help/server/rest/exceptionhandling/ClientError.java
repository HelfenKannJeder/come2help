package de.helfenkannjeder.come2help.server.rest.exceptionhandling;

import org.springframework.validation.FieldError;

public class ClientError {

    public static ClientError fromFieldError(FieldError fieldError) {
        ClientError clientError = new ClientError();
        clientError.path = fieldError.getField();
        clientError.code = fieldError.getDefaultMessage();
        clientError.value = fieldError.getRejectedValue().toString();

        return clientError;
    }

    public String path;
    public String code;
    public String value;
}
