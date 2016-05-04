package de.helfenkannjeder.come2help.server.cucumber.util;

import de.helfenkannjeder.come2help.server.rest.dto.AddressDto;

public class AddressDtoObjectMother {

    public static AddressDto anyValidAddressDto() {
        return AddressDto.createFullDto(AddressObjectMother.anyValidAddress());
    }

    public static AddressDto anyFullAddressDto() {
        return AddressDto.createFullDto(AddressObjectMother.anyFullAddress());
    }
}
