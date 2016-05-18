package de.helfenkannjeder.come2help.server.cucumber.util;

import de.helfenkannjeder.come2help.server.domain.Volunteer;

public class VolunteerObjectMother {

    public static Volunteer anyValidVolunteer() {
        return new Volunteer().setUser(UserObjectMother.anyValidUser());
    }

    public static Volunteer anyInvalidVolunteer() {
        return anyValidVolunteer().setUser(UserObjectMother.anyInvalidUser());
    }
}
