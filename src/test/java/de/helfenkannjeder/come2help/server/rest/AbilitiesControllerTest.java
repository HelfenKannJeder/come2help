package de.helfenkannjeder.come2help.server.rest;

import de.helfenkannjeder.come2help.server.rest.dto.AbilityDto;
import de.helfenkannjeder.come2help.server.security.Authorities;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public class AbilitiesControllerTest extends AbstractControllerTest {

    @Test
    public void getAbilities_withAbilitiesFromDatabase_returnsSerializedAbilities() throws Exception {
        authenticate(Authorities.USER);

        mockMvc.perform(get("/abilities").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
        // TODO: Check sort order
    }

    @Test
    public void createAbility_withNewAbility_returnsCreatedAbility() throws Exception {
        authenticate(Authorities.USER, Authorities.C2H_ADMIN);

        String name = "Test Ability";
        String description = "Test Description";
        AbilityDto abilityDto = new AbilityDto(null, name, description);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/abilities").accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(abilityDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.description", is(description)));

        // Assert
        mockMvc.perform(get("/abilities").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void getAbilityById_withAbilityFromDatabase_returnsSerializedAbility() throws Exception {
        authenticate(Authorities.USER);

        // Act
        mockMvc.perform(get("/abilities/1").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.name", is("Translate documents")))
                .andExpect(jsonPath("$.description", is("Able to translate documents")));
    }

    @Test
    public void updateAbility_withSerializedAbility_returnsNewAbilityAndVerifyUpdateInDatabase() throws Exception {
        // Arrange
        authenticate(Authorities.USER, Authorities.C2H_ADMIN);

        String name = "Updated Ability";
        String description = "Updated Description";
        AbilityDto abilityDto = new AbilityDto(null, name, description);
        ObjectMapper objectMapper = new ObjectMapper();

        // Act
        mockMvc.perform(put("/abilities/2").accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(abilityDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.description", is(description)));

        // Assert
        mockMvc.perform(get("/abilities/2").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.description", is(description)));
    }

    @Test
    public void deleteAbility_withAbilityFromDatabase_verifyAbilityCount() throws Exception {
        // Arrange
        authenticate(Authorities.USER, Authorities.C2H_ADMIN);

        // Act
        mockMvc.perform(delete("/abilities/2"))
                .andExpect(status().isNoContent());


        // Assert
        mockMvc.perform(get("/abilities").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }


    @Test
    public void deleteAbility_withNotExistingAbility_verifyAbilityCount() throws Exception {
        // Arrange
        authenticate(Authorities.USER, Authorities.C2H_ADMIN);

        // Act
        mockMvc.perform(delete("/abilities/200"))
                .andExpect(status().isNotFound());


        // Assert
        mockMvc.perform(get("/abilities").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}