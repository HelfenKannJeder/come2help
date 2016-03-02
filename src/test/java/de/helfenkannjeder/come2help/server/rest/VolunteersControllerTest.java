package de.helfenkannjeder.come2help.server.rest;

import de.helfenkannjeder.come2help.server.rest.dto.AddressDto;
import de.helfenkannjeder.come2help.server.rest.dto.UserDto;
import de.helfenkannjeder.come2help.server.rest.dto.VolunteerDto;
import de.helfenkannjeder.come2help.server.security.Authorities;
import org.codehaus.jackson.map.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public class VolunteersControllerTest extends AbstractControllerTest {

    @Test
    public void createVolunteer_withSerializedVolunteer_returnsVolunteerWithAccessToken() throws Exception {
        dummyAuthentication(Authorities.GUEST);

        ObjectMapper objectMapper = new ObjectMapper();
        VolunteerDto volunteerDto = new VolunteerDto(null,
                new UserDto(
                        "testMail@helfenkannjeder.de",
                        "GivenName",
                        "Surname",
                        new AddressDto("76137", null, null, null),
                        "",
                        true),
                Collections.emptyList());
        mockMvc.perform(post("/volunteers")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(volunteerDto)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Authorization", CoreMatchers.anything()));
    }
}