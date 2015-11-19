/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Address;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Andreas Eberle
 */
public class AddressDtoTest {

    @Test
    public void testAddressToAddressDto() {
        Address address = new Address(42L, "2323", "Karlsruhe", "Kaiserstraße", "6217");
        AddressDto dto = AddressDto.createFullDto(address);

        assertEqual(address, dto);
    }

    public static void assertEqual(Address address, AddressDto dto) {
        assertEquals(address.getId(), dto.getId());
        assertEquals(address.getZipCode(), dto.getZipCode());
        assertEquals(address.getCity(), dto.getCity());
        assertEquals(address.getStreet(), dto.getStreet());
        assertEquals(address.getStreetNumber(), dto.getStreetNumber());
    }

    @Test
    public void testAddressDtoToAddress() {
        AddressDto dto = new AddressDto(42L, "2323", "Karlsruhe", "Kaiserstraße", "6217");
        Address address = AddressDto.createAddress(dto);

        assertEqual(dto, address);
    }

    public static void assertEqual(AddressDto dto, Address address) {
        assertEquals(dto.getId(), address.getId());
        assertEquals(dto.getZipCode(), address.getZipCode());
        assertEquals(dto.getCity(), address.getCity());
        assertEquals(dto.getStreet(), address.getStreet());
        assertEquals(dto.getStreetNumber(), address.getStreetNumber());
    }
}
