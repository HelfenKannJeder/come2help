package de.helfenkannjeder.come2help.server.cucumber.util;

import de.helfenkannjeder.come2help.server.rest.dto.AddressDto;

public class AdressObjectMother {

    public static AddressDto anyValidAddress() {
        return new AddressDto()
                .setZipCode("76185");
    }
}
