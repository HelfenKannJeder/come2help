package de.helfenkannjeder.come2help.server.cucumber;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Iterables;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import de.helfenkannjeder.come2help.server.cucumber.configuration.TestApplicationConfiguration;
import de.helfenkannjeder.come2help.server.cucumber.util.AbilityApiRestClient;
import de.helfenkannjeder.come2help.server.cucumber.util.VolunteerApiRestClient;
import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityDto;
import de.helfenkannjeder.come2help.server.rest.dto.VolunteerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestApplicationConfiguration.class)
public class Come2helpApiTestFacade {

    private final VolunteerApiRestClient volunteerApiRestClient;
    private final AbilityApiRestClient abilityApiRestClient;

    private List<VolunteerDto> createdVolunteers;
    private List<AbilityDto> createdAbilities;

    private HttpStatus latestRelevantStatusCode;

    @Autowired
    public Come2helpApiTestFacade(VolunteerApiRestClient volunteerApiRestClient,
                                  AbilityApiRestClient abilityApiRestClient) {
        this.volunteerApiRestClient = volunteerApiRestClient;
        this.abilityApiRestClient = abilityApiRestClient;
    }

    @Before
    public void beforeScenario() {
        createdVolunteers = new ArrayList<>();
        createdAbilities = new ArrayList<>();
    }

    @After
    public void afterScenario() {
        doCleanup();
    }

    public VolunteerDto getLastCreatedVolunteer() {
        return Iterables.getLast(createdVolunteers);
    }

    public AbilityDto getLastCreatedAbility() {
        return Iterables.getLast(createdAbilities);
    }

    public ResponseEntity<VolunteerDto> updateVolunteer(VolunteerDto volunteer) {
        ResponseEntity<VolunteerDto> responseEntity = volunteerApiRestClient.updateVolunteer(volunteer);
        latestRelevantStatusCode = responseEntity.getStatusCode();
        return responseEntity;
    }

    public ResponseEntity<AbilityDto> updateAbility(AbilityDto abilityDto) {
        ResponseEntity<AbilityDto> responseEntity = abilityApiRestClient.updateAbility(abilityDto);
        latestRelevantStatusCode = responseEntity.getStatusCode();
        return responseEntity;
    }

    public ResponseEntity<VolunteerDto> createVolunteer(VolunteerDto volunteer) {
        ResponseEntity<VolunteerDto> responseEntity = volunteerApiRestClient.createVolunteer(volunteer);
        if (responseEntity.hasBody()) {
            createdVolunteers.add(responseEntity.getBody());
            latestRelevantStatusCode = responseEntity.getStatusCode();
        }
        return responseEntity;
    }

    public ResponseEntity<AbilityDto> createAbility(AbilityDto abilityDto) {
        ResponseEntity<AbilityDto> responseEntity = abilityApiRestClient.createAbility(abilityDto);
        if (responseEntity.hasBody()) {
            createdAbilities.add(responseEntity.getBody());
            latestRelevantStatusCode = responseEntity.getStatusCode();
        }
        return responseEntity;
    }

    public ResponseEntity<VolunteerDto> getVolunteer(Long id) {
        return volunteerApiRestClient.getVolunteer(id);
    }
    public ResponseEntity<AbilityDto> getAbility(Long id) {
        return abilityApiRestClient.getAbility(id);
    }


    public HttpStatus deleteVolunteer(Long id) {
        latestRelevantStatusCode = volunteerApiRestClient.deleteVolunteer(id);
        return latestRelevantStatusCode;
    }

    public HttpStatus deleteAbility(Long id) {
        latestRelevantStatusCode = abilityApiRestClient.deleteAbility(id);
        return latestRelevantStatusCode;
    }

    public HttpStatus getLatestHttpStatusCode() {
        return latestRelevantStatusCode;
    }

    private void doCleanup() {
        for (VolunteerDto volunteer : createdVolunteers) {
            volunteerApiRestClient.deleteVolunteer(volunteer.getId());
        }
        createdVolunteers.clear();
        for (AbilityDto ability : createdAbilities) {
            abilityApiRestClient.deleteAbility(ability.getId());
        }
        createdAbilities.clear();
    }
}
