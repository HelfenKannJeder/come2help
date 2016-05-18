package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.cucumber.util.AddressDtoObjectMother;
import de.helfenkannjeder.come2help.server.cucumber.util.AddressObjectMother;
import de.helfenkannjeder.come2help.server.domain.Address;
import org.junit.Test;

import static de.helfenkannjeder.come2help.server.matchers.AddressDtoMatcher.matchesAddressDto;
import static de.helfenkannjeder.come2help.server.matchers.AddressMatcher.matchesAddress;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddressDtoTest {

    @Test
    public void testAddressToAddressDto() {
        Address address = AddressObjectMother.anyFullAddress();
        AddressDto dto = AddressDto.createFullDto(address);

        assertThat(dto, matchesAddressDto().withZipCode(address.getZipCode())
                .withCity(address.getCity())
                .withStreet(address.getStreet())
                .withStreetNumber(address.getStreetNumber()));
    }

    @Test
    public void testAddressDtoToAddress() {
        AddressDto dto = AddressDtoObjectMother.anyFullAddressDto();
        Address address = AddressDto.createAddress(dto);

        assertThat(address, matchesAddress().withZipCode(dto.getZipCode())
                .withCity(dto.getCity())
                .withStreet(dto.getStreet())
                .withStreetNumber(dto.getStreetNumber()));
    }
}
