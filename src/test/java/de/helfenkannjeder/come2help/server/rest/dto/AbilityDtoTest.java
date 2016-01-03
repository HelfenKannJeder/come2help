package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Ability;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AbilityDtoTest {

    @Test
    public void testAbilityToAbilityDto() {
        Ability ability = new Ability(1233L, "name", "description", new Ability(1L, "test", "desc", null, true, true), true, true);
        AbilityDto dto = AbilityDto.createFullDto(ability);

        assertEqual(ability, dto);
    }

    public static void assertEqual(Ability ability, AbilityDto dto) {
        assertEquals(ability.getId(), dto.getId());
        assertEquals(ability.getName(), dto.getName());
        assertEquals(ability.getDescription(), dto.getDescription());
        assertEquals(ability.getParentAbility(), dto.getParentAbility());
        assertEquals(ability.isCategory(), dto.isCategory());
        assertEquals(ability.isSelectable(), dto.isSelectable());
    }

    @Test
    public void testAbilityDtoToAbility() {
        AbilityDto dto = new AbilityDto(1234L, "name", "description", null, true, true);
        Ability ability = AbilityDto.createAbility(dto);

        assertEqual(dto, ability);
    }

    public static void assertEqual(AbilityDto dto, Ability ability) {
        assertEquals(dto.getId(), ability.getId());
        assertEquals(dto.getName(), ability.getName());
        assertEquals(dto.getDescription(), ability.getDescription());
        assertEquals(dto.getParentAbility(), ability.getParentAbilityId());
        assertEquals(dto.isCategory(), ability.isCategory());
        assertEquals(dto.isSelectable(), ability.isSelectable());

    }
}
