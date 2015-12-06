package de.helfenkannjeder.come2help.server.cucumber.util;

import de.helfenkannjeder.come2help.server.rest.dto.AbilityCategoryDto;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityDto;
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
public class AbilityApiRestClient {

    private final RestTemplate restTemplate;
    private final String serviceBaseUrl;

    @Autowired
    public AbilityApiRestClient(RestTemplate restTemplate, @Value("${come2help.baseUrl}") String serviceBaseUrl) {
        this.restTemplate = restTemplate;
        this.serviceBaseUrl = serviceBaseUrl;
    }

    public ResponseEntity<AbilityDto> createAbility(AbilityDto abilityDto) {
        return restTemplate.exchange(getAbilitiesUrl(), HttpMethod.POST, createHttpEntity(abilityDto), AbilityDto.class);
    }

    public ResponseEntity<AbilityDto> getAbility(Long id) {
        return restTemplate.exchange(getAbilitiesUrl(), HttpMethod.GET, null, AbilityDto.class, id);
    }

    public ResponseEntity<AbilityDto> updateAbility(AbilityDto abilityDto) {
        return restTemplate.exchange(getAbilityUrl(), HttpMethod.PUT, createHttpEntity(abilityDto), AbilityDto.class, abilityDto.getId());
    }

    public HttpStatus deleteAbility(Long id) {
        ResponseEntity<Void> responseEntity = restTemplate.exchange(getAbilityUrl(), HttpMethod.DELETE, null, Void.class, id);

        return responseEntity.getStatusCode();
    }

    private String getAbilitiesUrl() {
        return serviceBaseUrl + "/abilities";
    }

    private String getAbilityUrl() {
        return getAbilitiesUrl() + "/{id}";
    }

    private <T> HttpEntity<T> createHttpEntity(T body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, httpHeaders);
    }
}
