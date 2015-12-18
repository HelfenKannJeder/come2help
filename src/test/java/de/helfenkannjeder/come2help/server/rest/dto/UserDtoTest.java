package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.cucumber.util.UserDtoObjectMother;
import de.helfenkannjeder.come2help.server.cucumber.util.UserObjectMother;
import de.helfenkannjeder.come2help.server.domain.User;
import org.junit.Test;

import static de.helfenkannjeder.come2help.server.matchers.UserMatcher.matchesUser;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserDtoTest {

    @Test
    public void testUserToUserDto() {
        User user = UserObjectMother.anyValidUser();
        UserDto dto = UserDto.createFullDto(user);

        assertThat(user, matchesUser().withEmail(dto.getEmail())
                .withGivenName(dto.getGivenName())
                .withSurname(dto.getSurname())
                .withAddress(AddressDto.createAddress(dto.getAddress()))
                .withPhone(dto.getPhone()));
    }

    @Test
    public void testVolunteerDtoToVolunteer() {
        UserDto dto = UserDtoObjectMother.anyValidUserDto();
        User user = UserDto.createUser(dto);

        assertThat(user, matchesUser().withEmail(dto.getEmail())
                .withSurname(dto.getSurname())
                .withGivenName(dto.getGivenName())
                .withAddress(AddressDto.createAddress(dto.getAddress()))
                .withPhone(dto.getPhone()));
    }
}
