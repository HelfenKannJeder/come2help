package de.helfenkannjeder.come2help.server.cucumber.stepdefinitions;

import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.helfenkannjeder.come2help.server.cucumber.Come2helpApiTestFacade;
import de.helfenkannjeder.come2help.server.cucumber.configuration.TestApplicationConfiguration;
import de.helfenkannjeder.come2help.server.cucumber.transformers.HTTPStatusTransformer;
import de.helfenkannjeder.come2help.server.cucumber.util.AbilityCategoryObjectMother;
import de.helfenkannjeder.come2help.server.domain.AbilityCategory;
import de.helfenkannjeder.come2help.server.matchers.AbilityCategoryDtoMatcher;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityCategoryDto;
import org.hamcrest.CoreMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ContextConfiguration(classes = TestApplicationConfiguration.class)
public class AbilityCategorySteps {

    private final Come2helpApiTestFacade facade;
    private AbilityCategoryDto abilityCategoryDto = new AbilityCategoryDto();
    private ResponseEntity<AbilityCategoryDto> createAbilityCategoryResponseEntity;
    private ResponseEntity<AbilityCategoryDto> updateAbilityCategoryResponseEntity;

    @Autowired
    public AbilityCategorySteps(Come2helpApiTestFacade facade) {
        this.facade = facade;
    }

    @Given("^any valid abilitycategory$")
    public void any_valid_abilitycategory() throws Throwable {
        abilityCategoryDto = AbilityCategoryObjectMother.anyValidAbilityCategory();
    }

    @Given("^any invalid abilitycategory$")
    public void any_invalid_abilitycategory() throws Throwable {
        abilityCategoryDto = AbilityCategoryObjectMother.anyInvalidAbilityCategory();
    }

    @When("^the abilitycategory is created$")
    public void the_abilitycategory_is_created() throws Throwable {
        the_abilitycategory_creation_request_is_send();
        assertThat("could not create the abilityCategory", facade.getLatestHttpStatusCode().is2xxSuccessful());
    }

    @When("^the abilitycategory create request is send$")
    public void the_abilitycategory_creation_request_is_send() throws Throwable {
        createAbilityCategoryResponseEntity = facade.createAbilityCategory(abilityCategoryDto);
        abilityCategoryDto = createAbilityCategoryResponseEntity.getBody();
    }

    @Then("^the response contains the created abilitycategory$")
    public void the_response_contains_the_created_abilitycategory() throws Throwable {
        assertThat(createAbilityCategoryResponseEntity.getBody(), CoreMatchers.is(AbilityCategoryDtoMatcher.matchesAbilityCategory(facade.getLastCreatedAbilityCategory())));
    }

    @Given("^an existing abilitycategory$")
    public void an_existing_abilitycategory() throws Throwable {
        any_valid_abilitycategory();
        the_abilitycategory_is_created();
    }

    @Given("^the user changes a property of the abilitycategory")
    public void the_user_changes_a_property_of_the_abilitycategory() throws Throwable {
        abilityCategoryDto = createAbilityCategoryResponseEntity.getBody();
        abilityCategoryDto.setParentAbilityCategory(new AbilityCategory(1L, "abc", null));
    }

    @When("^the abilitycategory is updated$")
    public void the_abilitycategory_is_updated() throws Throwable {
        updateAbilityCategoryResponseEntity = facade.updateAbilityCategory(abilityCategoryDto);
        abilityCategoryDto = updateAbilityCategoryResponseEntity.getBody();
    }

    @When("^the abilitycategory is deleted")
    public void the_abilitycategory_is_deleted() throws Throwable {
        facade.deleteAbilityCategory(abilityCategoryDto.getId());
    }

    @Then("^the updated abilitycategory is returned$")
    public void the_updated_abilitycategory_is_returned() throws Throwable {
        assertThat(updateAbilityCategoryResponseEntity.getBody(), CoreMatchers.is(AbilityCategoryDtoMatcher.matchesAbilityCategory(abilityCategoryDto)));
    }

    @Given("^a non existing abilitycategory$")
    public void a_non_existing_abilitycategory() throws Throwable {
        any_valid_abilitycategory();
        abilityCategoryDto.setId(-5L);
    }

    @Then("^the returned status code for the abilitycategory is (.+)$")
    public void theReturnedStatusCodeIs(@Transform(HTTPStatusTransformer.class) HttpStatus httpStatus) throws Throwable {
        assertThat(facade.getLatestHttpStatusCode(), is(httpStatus));
    }

    @When("^the abilitycategory id is re-used$")
    public void theAbilityCategoryIdIsReUsed() throws Throwable {
        abilityCategoryDto = createAbilityCategoryResponseEntity.getBody();
        abilityCategoryDto.setName("abc");
        the_abilitycategory_creation_request_is_send();
    }

    @And("^the user modifies the abilitycategory with invalid data$")
    public void theUserModifiesTheAbilityCategoryWithInvalidData() throws Throwable {
        abilityCategoryDto = createAbilityCategoryResponseEntity.getBody();
        abilityCategoryDto.setName(null);
    }
}
