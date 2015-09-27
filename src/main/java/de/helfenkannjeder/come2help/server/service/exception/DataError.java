package de.helfenkannjeder.come2help.server.service.exception;

public class DataError {
    public String code;
    public String value;

    public DataError(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
