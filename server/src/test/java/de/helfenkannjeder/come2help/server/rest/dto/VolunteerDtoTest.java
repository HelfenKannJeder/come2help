package de.helfenkannjeder.come2help.server.rest.dto;

import java.util.Collections;

import de.helfenkannjeder.come2help.server.cucumber.util.UserObjectMother;
import de.helfenkannjeder.come2help.server.cucumber.util.VolunteerDtoObjectMother;
import de.helfenkannjeder.come2help.server.domain.User;
import de.helfenkannjeder.come2help.server.domain.Volunteer;
import org.junit.Test;

import static de.helfenkannjeder.come2help.server.matchers.VolunteerDtoMatcher.matchesVolunteerDto;
import static de.helfenkannjeder.come2help.server.matchers.VolunteerMatcher.matchesVolunteer;
import static org.hamcrest.MatcherAssert.assertThat;

public class VolunteerDtoTest {

    @Test
    public void testVolunteerToVolunteerDto() {
        User user = UserObjectMother.anyValidUser();
        Volunteer volunteer = new Volunteer(234L, user, Collections.emptyList());
        VolunteerDto dto = VolunteerDto.createFullDto(volunteer);

        assertThat(dto, matchesVolunteerDto().withId(volunteer.getId()));
    }

    @Test
    public void testVolunteerDtoToVolunteer() {
        VolunteerDto dto = VolunteerDtoObjectMother.anyValidVolunteerDto();
        Volunteer volunteer = VolunteerDto.createVolunteer(dto);

        assertThat(volunteer, matchesVolunteer().withId(dto.getId()));
    }
}
