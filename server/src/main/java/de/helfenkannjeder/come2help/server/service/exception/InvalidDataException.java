package de.helfenkannjeder.come2help.server.service.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

public class InvalidDataException extends RuntimeException {

    private static final long serialVersionUID = 5388741957708978726L;

    public static InvalidDataException forSingleError(String code, String value) {
        DataError error = new DataError(code, value);

        return new InvalidDataException(Collections.singletonList(error));
    }

    private final ArrayList<DataError> errors = Lists.newArrayList();

    public InvalidDataException(List<DataError> errors) {
        this.errors.addAll(errors);
    }

    public List<DataError> getErrors() {
        return errors;
    }
}
