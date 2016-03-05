package de.helfenkannjeder.come2help.server.cucumber.util;

import de.helfenkannjeder.come2help.server.rest.dto.UserDto;

public class UserDtoObjectMother {

    public static UserDto anyValidUserDto() {
        return UserDto.createFullDto(UserObjectMother.anyValidUser());
    }

    public static UserDto anyInvalidUserDto() {
        return UserDto.createFullDto(UserObjectMother.anyInvalidUser());
    }
}
