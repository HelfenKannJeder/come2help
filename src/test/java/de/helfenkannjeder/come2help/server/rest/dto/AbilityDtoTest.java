/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Ability;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Andreas Eberle
 */
public class AbilityDtoTest {

    @Test
    public void testAbilityToAbilityDto() {
        Ability address = new Ability(1233L, "name", "description");
        AbilityDto dto = AbilityDto.createFullDto(address);

        assertEqual(address, dto);
    }

    public static void assertEqual(Ability address, AbilityDto dto) {
        assertEquals(address.getId(), dto.getId());
        assertEquals(address.getName(), dto.getName());
        assertEquals(address.getDescription(), dto.getDescription());
    }

    @Test
    public void testAddressDtoToAddress() {
        AbilityDto dto = new AbilityDto(1234L, "name", "description");
        Ability address = AbilityDto.createAbility(dto);

        assertEqual(dto, address);
    }

    public static void assertEqual(AbilityDto dto, Ability address) {
        assertEquals(dto.getId(), address.getId());
        assertEquals(dto.getName(), address.getName());
        assertEquals(dto.getDescription(), address.getDescription());
    }
}
