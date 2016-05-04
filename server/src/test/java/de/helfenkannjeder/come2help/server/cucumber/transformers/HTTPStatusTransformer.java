package de.helfenkannjeder.come2help.server.cucumber.transformers;

import cucumber.api.Transformer;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public class HTTPStatusTransformer extends Transformer<HttpStatus> {
    @Override
    public HttpStatus transform(String s) {
        String[] parts = s.trim().split(" ");
        if(parts.length < 2 || !parts[0].equalsIgnoreCase("http")) {
            throw new IllegalArgumentException(String.format("Format should be HTTP 200 OK but was %s", s));
        }

        Integer statusCode = Integer.parseInt(parts[1]);

        return valueOf(statusCode);
    }
}
