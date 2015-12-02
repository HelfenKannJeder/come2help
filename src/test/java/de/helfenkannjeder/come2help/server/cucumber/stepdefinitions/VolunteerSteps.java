package de.helfenkannjeder.come2help.server.cucumber.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.helfenkannjeder.come2help.server.cucumber.Come2helpApiTestFacade;
import de.helfenkannjeder.come2help.server.cucumber.configuration.TestApplicationConfiguration;
import de.helfenkannjeder.come2help.server.cucumber.transformers.HTTPStatusTransformer;
import de.helfenkannjeder.come2help.server.cucumber.util.VolunteerObjectMother;
import de.helfenkannjeder.come2help.server.matchers.VolunteerDtoMatcher;
import de.helfenkannjeder.come2help.server.rest.dto.VolunteerDto;
import org.hamcrest.CoreMatchers;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestApplicationConfiguration.class)
public class VolunteerSteps {

    private final Come2helpApiTestFacade facade;
    private VolunteerDto volunteerDto = new VolunteerDto();
    private ResponseEntity<VolunteerDto> createVolunteerResponseEntity;
    private ResponseEntity<VolunteerDto> updateVolunteerResponseEntity;

    @Autowired
    public VolunteerSteps(Come2helpApiTestFacade facade) {
        this.facade = facade;
    }

    @Given("^any valid volunteer$")
    public void any_valid_volunteer() throws Throwable {
        volunteerDto = VolunteerObjectMother.anyValidVolunteer();
    }

    @Given("^any invalid volunteer$")
    public void any_invalid_volunteer() throws Throwable {
        volunteerDto = VolunteerObjectMother.anyInvalidVolunteer();
    }

    @When("^the volunteer is created$")
    public void the_volunteer_is_created() throws Throwable {
        the_volunteer_creation_request_is_send();
        assertThat("could not create the volunteer", facade.getLatestHttpStatusCode().is2xxSuccessful());
    }

    @When("^the volunteer create request is send$")
    public void the_volunteer_creation_request_is_send() throws Throwable {
        createVolunteerResponseEntity = facade.createVolunteer(volunteerDto);
        volunteerDto = createVolunteerResponseEntity.getBody();
    }

    @Then("^the response contains the created volunteer$")
    public void the_response_contains_the_created_volunteer() throws Throwable {
        assertThat(createVolunteerResponseEntity.getBody(), CoreMatchers.is(VolunteerDtoMatcher.matchesVolunteer(facade.getLastCreatedVolunteer())));
    }

    @Given("^an existing volunteer$")
    public void an_existing_volunteer() throws Throwable {
        any_valid_volunteer();
        the_volunteer_is_created();
    }

    @Given("^the user changes a property of the volunteer")
    public void the_user_changes_a_property_of_the_volunteer() throws Throwable {
        volunteerDto = createVolunteerResponseEntity.getBody();
        volunteerDto.setSurname("ChangedName");
    }

    @When("^the volunteer is updated$")
    public void the_volunteer_is_updated() throws Throwable {
        updateVolunteerResponseEntity = facade.updateVolunteer(volunteerDto);
        volunteerDto = updateVolunteerResponseEntity.getBody();
    }

    @When("^the volunteer is deleted")
    public void the_volunteer_is_deleted() throws Throwable {
        facade.deleteVolunteer(volunteerDto.getId());
    }

    @Then("^the updated volunteer is returned$")
    public void the_updated_volunteer_is_returned() throws Throwable {
        assertThat(updateVolunteerResponseEntity.getBody(), CoreMatchers.is(VolunteerDtoMatcher.matchesVolunteer(volunteerDto)));
    }

    @Given("^a non existing volunteer$")
    public void a_non_existing_volunteer() throws Throwable {
        any_valid_volunteer();
        volunteerDto.setId(-5L);
    }

    @Then("^the returned status code is (.+)$")
    public void theReturnedStatusCodeIs(@Transform(HTTPStatusTransformer.class) HttpStatus httpStatus) throws Throwable {
        assertThat(facade.getLatestHttpStatusCode(), is(httpStatus));
    }

    @When("^the volunteer id is re-used$")
    public void theVolunteerIdIsReUsed() throws Throwable {
        volunteerDto = createVolunteerResponseEntity.getBody();
        volunteerDto.setSurname("Testname");
        volunteerDto.setEmail("test@test.come2.help");
        the_volunteer_creation_request_is_send();
    }

    @And("^the user modifies the volunteer with invalid data$")
    public void theUserModifiesTheVolunteerWithInvalidData() throws Throwable {
        volunteerDto = createVolunteerResponseEntity.getBody();
        volunteerDto.setEmail("invalidEmail");
    }
}
