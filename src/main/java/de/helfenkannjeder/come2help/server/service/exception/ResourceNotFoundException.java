package de.helfenkannjeder.come2help.server.service.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 4062618674483457639L;

    public ResourceNotFoundException(Object id) {
        super(String.format("Object with ID %s has not been found", id));
    }
}
