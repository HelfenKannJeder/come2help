package de.helfenkannjeder.come2help.server.cucumber.util;

import de.helfenkannjeder.come2help.server.domain.User;

public class UserObjectMother {

    public static User anyValidUser() {
        return new User()
                .setGivenName("Max")
                .setSurname("Muster")
                .setEmail("max@muster.com")
                .setAddress(AddressObjectMother.anyValidAddress())
                .setPhone("+49232");
    }

    public static User anyInvalidUser() {
        return anyValidUser().setEmail("invalidEmail");
    }
}
