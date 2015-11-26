package de.helfenkannjeder.come2help.server.cucumber.stepdefinitions;

import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.helfenkannjeder.come2help.server.cucumber.Come2helpApiTestFacade;
import de.helfenkannjeder.come2help.server.cucumber.configuration.TestApplicationConfiguration;
import de.helfenkannjeder.come2help.server.cucumber.transformers.HTTPStatusTransformer;
import de.helfenkannjeder.come2help.server.cucumber.util.AbilityObjectMother;
import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.matchers.AbilityDtoMatcher;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityDto;
import org.hamcrest.CoreMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ContextConfiguration(classes = TestApplicationConfiguration.class)
public class AbilitySteps {

    private final Come2helpApiTestFacade facade;
    private AbilityDto abilityDto = new AbilityDto();
    private ResponseEntity<AbilityDto> createAbilityResponseEntity;
    private ResponseEntity<AbilityDto> updateAbilityResponseEntity;

    @Autowired
    public AbilitySteps(Come2helpApiTestFacade facade) {
        this.facade = facade;
    }

    @Given("^any valid ability$")
    public void any_valid_ability() throws Throwable {
        abilityDto = AbilityObjectMother.anyValidAbility();
    }

    @Given("^any invalid ability$")
    public void any_invalid_ability() throws Throwable {
        abilityDto = AbilityObjectMother.anyInvalidAbility();
    }

    @When("^the ability is created$")
    public void the_ability_is_created() throws Throwable {
        the_ability_creation_request_is_send();
        assertThat("could not create the ability", facade.getLatestHttpStatusCode().is2xxSuccessful());
    }

    @When("^the ability create request is send$")
    public void the_ability_creation_request_is_send() throws Throwable {
        createAbilityResponseEntity = facade.createAbility(abilityDto);
    }

    @Then("^the response contains the created ability$")
    public void the_response_contains_the_created_ability() throws Throwable {
        assertThat(createAbilityResponseEntity.getBody(), CoreMatchers.is(AbilityDtoMatcher.matchesAbility(facade.getLastCreatedAbility())));
    }

    @Given("^an existing ability$")
    public void an_existing_ability() throws Throwable {
        any_valid_ability();
        the_ability_is_created();
    }

    @Given("^the user changes a property of the ability")
    public void the_user_changes_a_property_of_the_ability() throws Throwable {
        abilityDto = createAbilityResponseEntity.getBody();
        abilityDto.setDescription("ChangedDescription");
    }

    @When("^the ability is updated$")
    public void the_ability_is_updated() throws Throwable {
        updateAbilityResponseEntity = facade.updateAbility(abilityDto);
    }

    @When("^the ability is deleted")
    public void the_ability_is_deleted() throws Throwable {
        facade.deleteAbility(abilityDto.getId());
    }

    @Then("^the updated ability is returned$")
    public void the_updated_ability_is_returned() throws Throwable {
        assertThat(updateAbilityResponseEntity.getBody(), CoreMatchers.is(AbilityDtoMatcher.matchesAbility(abilityDto)));
    }

    @Given("^a non existing ability$")
    public void a_non_existing_ability() throws Throwable {
        any_valid_ability();
        abilityDto.setId(-5L);
    }

    @Then("^the returned status code for the ability is (.+)$")
    public void theReturnedStatusCodeIs(@Transform(HTTPStatusTransformer.class) HttpStatus httpStatus) throws Throwable {
        assertThat(facade.getLatestHttpStatusCode(), is(httpStatus));
    }

    @When("^the ability id is re-used$")
    public void theAbilityIdIsReUsed() throws Throwable {
        abilityDto = createAbilityResponseEntity.getBody();
        abilityDto.setName("abc");
        abilityDto.setDescription("driver license");
        the_ability_creation_request_is_send();
    }

    @And("^the user modifies the ability with invalid data$")
    public void theUserModifiesTheAbilityWithInvalidData() throws Throwable {
        abilityDto = createAbilityResponseEntity.getBody();
        abilityDto.setName(null);
    }
}
