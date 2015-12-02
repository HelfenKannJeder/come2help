package de.helfenkannjeder.come2help.server.cucumber.util;

import de.helfenkannjeder.come2help.server.rest.dto.VolunteerDto;

public class VolunteerObjectMother {

    public static VolunteerDto anyValidVolunteer() {
        return new VolunteerDto()
                .setGivenName("Max")
                .setSurname("Muster")
                .setAdult(true)
                .setAddress(AdressObjectMother.anyValidAddress());
    }

    public static VolunteerDto anyInvalidVolunteer() {
        return anyValidVolunteer().setEmail("invalidEmail");
    }
}
