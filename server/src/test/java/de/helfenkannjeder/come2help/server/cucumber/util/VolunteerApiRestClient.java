package de.helfenkannjeder.come2help.server.cucumber.util;

import de.helfenkannjeder.come2help.server.rest.dto.VolunteerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class VolunteerApiRestClient {

    private final RestTemplate restTemplate;
    private final String serviceBaseUrl;

    @Autowired
    public VolunteerApiRestClient(RestTemplate restTemplate, @Value("${come2help.baseUrl}") String serviceBaseUrl) {
        this.restTemplate = restTemplate;
        this.serviceBaseUrl = serviceBaseUrl;
    }

    public ResponseEntity<VolunteerDto> createVolunteer(VolunteerDto volunteerDto) {
        return restTemplate.exchange(getVolunteersUrl(), HttpMethod.POST, createHttpEntity(volunteerDto), VolunteerDto.class);
    }

    public ResponseEntity<VolunteerDto> getVolunteer(Long id) {
        return restTemplate.exchange(getVolunteersUrl(), HttpMethod.GET, null, VolunteerDto.class, id);
    }

    public ResponseEntity<VolunteerDto> updateVolunteer(VolunteerDto volunteerDto) {
        return restTemplate.exchange(getVolunteerUrl(), HttpMethod.PUT, createHttpEntity(volunteerDto), VolunteerDto.class, volunteerDto.getId());
    }

    public HttpStatus deleteVolunteer(Long id) {
        ResponseEntity<Void> responseEntity = restTemplate.exchange(getVolunteerUrl(), HttpMethod.DELETE, null, Void.class, id);

        return responseEntity.getStatusCode();
    }

    private String getVolunteersUrl() {
        return serviceBaseUrl + "/volunteers";
    }

    private String getVolunteerUrl() {
        return getVolunteersUrl() + "/{id}";
    }

    private <T> HttpEntity<T> createHttpEntity(T body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, httpHeaders);
    }
}
