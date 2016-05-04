package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Ability;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbilityResponseDtoTest {

    @Test
    public void testAbilityToAbilityResponseDto() {
        Ability ability = new Ability(1233L, "name", "description", new Ability(1L, "test", "desc", null, true, true), true, true);
        AbilityResponseDto dto = AbilityResponseDto.createFullDto(ability);

        assertEqual(ability, dto);
    }

    public static void assertEqual(Ability ability, AbilityResponseDto dto) {
        assertEquals(ability.getId(), dto.getId());
        assertEquals(ability.getName(), dto.getName());
        assertEquals(ability.getDescription(), dto.getDescription());
        assertEquals(ability.getParentAbilityId(), dto.getParentAbilityId());
        assertEquals(ability.isCategory(), dto.isCategory());
        assertEquals(ability.isSelectable(), dto.isSelectable());
    }
}
