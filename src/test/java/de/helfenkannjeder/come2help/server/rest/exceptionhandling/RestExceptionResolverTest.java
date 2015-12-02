package de.helfenkannjeder.come2help.server.rest.exceptionhandling;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.google.common.collect.Lists;
import de.helfenkannjeder.come2help.server.service.exception.DataError;
import de.helfenkannjeder.come2help.server.service.exception.DuplicateResourceException;
import de.helfenkannjeder.come2help.server.service.exception.InvalidDataException;
import de.helfenkannjeder.come2help.server.service.exception.ResourceNotFoundException;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestExceptionResolverTest {

    private final RestExceptionResolver restExceptionResolver = new RestExceptionResolver();
    private final HttpServletRequest servletRequest = mock(HttpServletRequest.class);

    @Test
    public void exceptionShouldBeAnsweredWithInternalServerError() {
        ResponseEntity<ErrorResponse> response = getResponseEntityForException();

        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void exceptionResponseBodyShouldContainIncidentId() {
        ErrorResponse response = getResponseEntityForException().getBody();

        assertThat(response.incidentId, notNullValue());
    }

    @Test
    public void exceptionResponseBodyShouldContainDefaultHttpStatusDescription() {
        ErrorResponse response = getResponseEntityForException().getBody();

        assertThat(response.description, is(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }

    @Test
    public void duplicateResourceExceptionShouldBeAnsweredWithConflict() {
        ResponseEntity<ErrorResponse> response = getResponseEntityForDuplicateResourceException("xy already exists");

        assertThat(response.getStatusCode(), is(HttpStatus.CONFLICT));
    }

    @Test
    public void duplicateResourceExceptionResponseBodyShouldContainExceptionMessageAsDescription() {

        ErrorResponse response = getResponseEntityForDuplicateResourceException("xy already exists").getBody();

        assertThat(response.description, is("xy already exists"));
    }

    @Test
    public void methodArgumentNotValidExceptionShouldBeAnsweredWithBadRequest() {
        ResponseEntity<ErrorResponse> response = getResponseEntityForMethodArgumentNotValidException();

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void methodArgumentNotValidExceptionResponseBodyShouldContainClientError() {
        ErrorResponse response = getResponseEntityForMethodArgumentNotValidException().getBody();

        assertThat(response.clientErrors, notNullValue());
        assertThat(response.clientErrors.size(), is(1));
        assertThat(response.clientErrors.get(0).path, is("fieldName"));
        assertThat(response.clientErrors.get(0).value, is("rejectedValue"));
        assertThat(response.clientErrors.get(0).code, is("invalid.value"));
    }

    @Test
    public void invalidDataExceptionShouldBeAnsweredWithBadRequest() {
        ResponseEntity<ErrorResponse> response = getResponseEntityForInvalidDataException();

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void resourceNotFoundExceptionShouldBeAnsweredWithNotFound() {
        ResponseEntity<ErrorResponse> response = getResponseEntityForResourceNotFoundException("xy not found");

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void httpMessageNotReadableExceptionWithInvalidJsonShouldBeAnsweredWithBadRequest() throws Exception {
        ResponseEntity<ErrorResponse> response = getResponseEntityForHttpMessageNotReadableExceptionWithJsonParseException();

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void httpMessageNotReadableExceptionWithInvalidJsonResponseBodyShouldContainDescription() throws Exception {
        ErrorResponse responseBody = getResponseEntityForHttpMessageNotReadableExceptionWithJsonParseException().getBody();

        assertThat(responseBody.description, notNullValue());
        assertThat(responseBody.description, containsString("Json parse not possible"));
    }

    @Test
    public void httpMessageNotReadableExceptionWithMissingBodyShouldBeAnsweredWithBadRequest() throws Exception {
        ResponseEntity<ErrorResponse> response = getResponseEntityForHttpMessageNotReadableExceptionWithMissingRequestBody();

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void httpMessageNotReadableExceptionWithMissingBodyResponseShouldContainDescription() throws Exception {
        ErrorResponse responseBody = getResponseEntityForHttpMessageNotReadableExceptionWithMissingRequestBody().getBody();

        assertThat(responseBody.description, notNullValue());
        assertThat(responseBody.description, containsString("Missing request body."));
    }

    @Test
    public void missingServletRequestParameterExceptionShouldBeAnsweredWithBadRequest() throws Exception {
        ResponseEntity<ErrorResponse> response = getResponseEntityForMissingServletRequestParameterException();

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void missingServletRequestParameterExceptionResponseShouldContainDescription() throws Exception {
        ErrorResponse responseBody = getResponseEntityForMissingServletRequestParameterException().getBody();

        assertThat(responseBody.description, notNullValue());
    }

    @Test
    public void httpMediaTypeNotSupportedExceptionShouldBeAnsweredWithUnsupportedMediaType() throws Exception {
        HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException = new HttpMediaTypeNotSupportedException("message");
        ResponseEntity<ErrorResponse> response = restExceptionResolver.resolveHttpMediaTypeNotSupportedException(servletRequest, httpMediaTypeNotSupportedException);

        assertThat(response.getStatusCode(), is(HttpStatus.UNSUPPORTED_MEDIA_TYPE));
    }

    private ResponseEntity<ErrorResponse> getResponseEntityForMethodArgumentNotValidException() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(Lists.newArrayList(new FieldError("objName", "fieldName", "rejectedValue", false, null, null, "invalid.value")));

        return restExceptionResolver.resolveMethodArgumentNotValidException(exception);
    }

    private ResponseEntity<ErrorResponse> getResponseEntityForInvalidDataException() {
        InvalidDataException exception = mock(InvalidDataException.class);
        when(exception.getErrors()).thenReturn(Lists.newArrayList(new DataError("code", "rejectedValue")));

        return restExceptionResolver.resolveInvalidDataException(exception);
    }

    private ResponseEntity<ErrorResponse> getResponseEntityForDuplicateResourceException(String message) {
        return restExceptionResolver.resolveDuplicateResourceException(servletRequest, new DuplicateResourceException(message));
    }

    private ResponseEntity<ErrorResponse> getResponseEntityForResourceNotFoundException(String message) {
        return restExceptionResolver.resolveResourceNotFoundException(servletRequest, new ResourceNotFoundException(message));
    }

    private ResponseEntity<ErrorResponse> getResponseEntityForException() {
        return restExceptionResolver.resolveException(new Exception("an error"));
    }

    private ResponseEntity<ErrorResponse> getResponseEntityForHttpMessageNotReadableExceptionWithJsonParseException() {
        JsonParseException jsonParseException = new JsonParseException("message", new JsonLocation(null, 3, 1, 0));
        HttpMessageNotReadableException httpMessageNotReadableException = new HttpMessageNotReadableException("message", jsonParseException);
        return restExceptionResolver.resolveHttpMessageNotReadableException(servletRequest, httpMessageNotReadableException);
    }

    private ResponseEntity<ErrorResponse> getResponseEntityForHttpMessageNotReadableExceptionWithMissingRequestBody() {
        HttpMessageNotReadableException httpMessageNotReadableException = new HttpMessageNotReadableException("Required request body is missing. some text");
        return restExceptionResolver.resolveHttpMessageNotReadableException(servletRequest, httpMessageNotReadableException);
    }

    private ResponseEntity<ErrorResponse> getResponseEntityForMissingServletRequestParameterException() {
        MissingServletRequestParameterException missingServletRequestParameterException = new MissingServletRequestParameterException("param1", "paramType");
        return restExceptionResolver.resolveMissingServletRequestParameterException(servletRequest, missingServletRequestParameterException);
    }
}
