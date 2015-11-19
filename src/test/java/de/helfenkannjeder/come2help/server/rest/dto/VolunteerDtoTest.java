/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Address;
import de.helfenkannjeder.come2help.server.domain.Volunteer;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Andreas Eberle
 */
public class VolunteerDtoTest {

    @Test
    public void testVolunteerToVolunteerDto() {
        Volunteer volunteer = new Volunteer(234L, "bla@bla.de", "Hans", "Maulwurf", new Address(42L, "2323", "Karlsruhe", "Kaiserstraße", "6217"), "+49 232", true);
        VolunteerDto dto = VolunteerDto.createFullDto(volunteer);

        assertEqual(volunteer, dto);
    }

    private void assertEqual(Volunteer volunteer, VolunteerDto dto) {
        Assert.assertEquals(volunteer.getId(), dto.getId());
        Assert.assertEquals(volunteer.getEmail(), dto.getEmail());
        Assert.assertEquals(volunteer.getGivenName(), dto.getGivenName());
        Assert.assertEquals(volunteer.getSurname(), dto.getSurname());
        AddressDtoTest.assertEqual(volunteer.getAddress(), dto.getAddress());
        Assert.assertEquals(volunteer.getPhone(), dto.getPhone());
        Assert.assertEquals(volunteer.isAdult(), dto.isAdult());
    }

    @Test
    public void testVolunteerDtoToVolunteer() {
        VolunteerDto dto = new VolunteerDto(234L, "bla@bla.de", "Hans", "Maulwurf", new AddressDto(42L, "2323", "Karlsruhe", "Kaiserstraße", "6217"), "+49 232", true);
        Volunteer volunteer = VolunteerDto.createVolunteer(dto);

        Assert.assertEquals(dto.getId(), volunteer.getId());
        Assert.assertEquals(dto.getEmail(), volunteer.getEmail());
        Assert.assertEquals(dto.getGivenName(), volunteer.getGivenName());
        Assert.assertEquals(dto.getSurname(), volunteer.getSurname());
        AddressDtoTest.assertEqual(dto.getAddress(), volunteer.getAddress());
        Assert.assertEquals(dto.getPhone(), volunteer.getPhone());
        Assert.assertEquals(dto.isAdult(), volunteer.isAdult());
    }
}
