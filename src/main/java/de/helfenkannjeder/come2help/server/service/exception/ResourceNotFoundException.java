package de.helfenkannjeder.come2help.server.service.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Object id) {
        super(String.format("Object with ID %s has not been found ", id));
    }
}
