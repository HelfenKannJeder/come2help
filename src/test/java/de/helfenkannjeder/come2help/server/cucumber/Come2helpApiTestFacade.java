package de.helfenkannjeder.come2help.server.cucumber;

import java.util.ArrayList;
import java.util.List;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import de.helfenkannjeder.come2help.server.cucumber.configuration.TestApplicationConfiguration;
import de.helfenkannjeder.come2help.server.cucumber.util.VolunteerApiRestClient;
import de.helfenkannjeder.come2help.server.rest.dto.VolunteerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestApplicationConfiguration.class)
public class Come2helpApiTestFacade {

    private final VolunteerApiRestClient volunteerApiRestClient;

    private List<VolunteerDto> createdVolunteers;

    private HttpStatus latestRelevantStatusCode;

    @Autowired
    public Come2helpApiTestFacade(VolunteerApiRestClient volunteerApiRestClient) {
        this.volunteerApiRestClient = volunteerApiRestClient;
    }

    @Before
    public void beforeScenario() {
        createdVolunteers = new ArrayList<>();
    }

    @After
    public void afterScenario() {
        doCleanup();
    }

    public VolunteerDto getLastCreatedVolunteer() {
        return createdVolunteers.get(createdVolunteers.size()-1);
    }

    public ResponseEntity<VolunteerDto> updateVolunteer(VolunteerDto volunteer) {
        ResponseEntity<VolunteerDto> responseEntity = volunteerApiRestClient.updateVolunteer(volunteer);
        latestRelevantStatusCode = responseEntity.getStatusCode();
        return responseEntity;
    }

    public ResponseEntity<VolunteerDto> createVolunteer(VolunteerDto volunteer) {
        ResponseEntity<VolunteerDto> responseEntity = volunteerApiRestClient.createVolunteer(volunteer);
        if (responseEntity.hasBody() && responseEntity.getStatusCode().is2xxSuccessful()) {
            VolunteerDto createdVolunteer = responseEntity.getBody();
            if(createdVolunteer != null && createdVolunteer.getId() != null) {
                createdVolunteers.add(createdVolunteer);
            }
        }
        latestRelevantStatusCode = responseEntity.getStatusCode();
        return responseEntity;
    }

    public ResponseEntity<VolunteerDto> getVolunteer(Long id) {
        return volunteerApiRestClient.getVolunteer(id);
    }

    public HttpStatus deleteVolunteer(Long id) {
        latestRelevantStatusCode = volunteerApiRestClient.deleteVolunteer(id);
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
    }
}
