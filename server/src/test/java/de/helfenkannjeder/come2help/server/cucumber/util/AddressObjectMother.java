package de.helfenkannjeder.come2help.server.cucumber.util;

import de.helfenkannjeder.come2help.server.domain.Address;

public class AddressObjectMother {

    public static Address anyValidAddress() {
        return new Address()
                .setZipCode("76185");
    }

    public static Address anyFullAddress() {
        return new Address()
                .setStreet("Kaiseralle")
                .setStreetNumber("1")
                .setCity("Karlsruhe")
                .setZipCode("76185");
    }
}
