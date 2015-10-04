package de.helfenkannjeder.come2help.server.service.exception;

import java.io.Serializable;

public class DataError implements Serializable {

    private static final long serialVersionUID = -2300011506801088463L;
    public final String code;
    public final String value;

    public DataError(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
