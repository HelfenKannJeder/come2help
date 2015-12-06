package de.helfenkannjeder.come2help.server.cucumber.util;

import de.helfenkannjeder.come2help.server.rest.dto.AbilityCategoryDto;
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
public class AbilityCategoryApiRestClient {

    private final RestTemplate restTemplate;
    private final String serviceBaseUrl;

    @Autowired
    public AbilityCategoryApiRestClient(RestTemplate restTemplate, @Value("${come2help.baseUrl}") String serviceBaseUrl) {
        this.restTemplate = restTemplate;
        this.serviceBaseUrl = serviceBaseUrl;
    }

    public ResponseEntity<AbilityCategoryDto> createAbilityCategory(AbilityCategoryDto abilityCategoryDto) {
        return restTemplate.exchange(getAbilityCategoriesUrl(), HttpMethod.POST, createHttpEntity(abilityCategoryDto), AbilityCategoryDto.class);
    }

    public ResponseEntity<AbilityCategoryDto> getAbilityCategory(Long id) {
        return restTemplate.exchange(getAbilityCategoriesUrl(), HttpMethod.GET, null, AbilityCategoryDto.class, id);
    }

    public ResponseEntity<AbilityCategoryDto> updateAbilityCategory(AbilityCategoryDto abilityCategoryDto) {
        return restTemplate.exchange(getAbilityCategoryUrl(), HttpMethod.PUT, createHttpEntity(abilityCategoryDto), AbilityCategoryDto.class, abilityCategoryDto.getId());
    }

    public HttpStatus deleteAbilityCategory(Long id) {
        ResponseEntity<Void> responseEntity = restTemplate.exchange(getAbilityCategoryUrl(), HttpMethod.DELETE, null, Void.class, id);

        return responseEntity.getStatusCode();
    }

    private String getAbilityCategoriesUrl() {
        return serviceBaseUrl + "/abilitycategories";
    }

    private String getAbilityCategoryUrl() {
        return getAbilityCategoriesUrl() + "/{id}";
    }

    private <T> HttpEntity<T> createHttpEntity(T body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, httpHeaders);
    }
}
