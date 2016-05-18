package de.helfenkannjeder.come2help.server.cucumber.util;

import de.helfenkannjeder.come2help.server.rest.dto.VolunteerDto;

public class VolunteerDtoObjectMother {

    public static VolunteerDto anyValidVolunteerDto() {
        return VolunteerDto.createFullDto(VolunteerObjectMother.anyValidVolunteer());
    }

    public static VolunteerDto anyInvalidVolunteerDto() {
        return VolunteerDto.createFullDto(VolunteerObjectMother.anyInvalidVolunteer());
    }
}
