package de.helfenkannjeder.come2help.server.service.exception;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class InvalidDataException extends RuntimeException {

    public static InvalidDataException forSingleError(String code, String value) {
        DataError error = new DataError(code, value);

        return new InvalidDataException(Collections.singletonList(error));
    }

    private final List<DataError> errors = Lists.newArrayList();

    public InvalidDataException(List<DataError> errors) {
        this.errors.addAll(errors);
    }

    public List<DataError> getErrors() {
        return errors;
    }
}
