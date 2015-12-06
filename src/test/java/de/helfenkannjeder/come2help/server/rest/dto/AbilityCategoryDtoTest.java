package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.domain.AbilityCategory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbilityCategoryDtoTest {

    @Test
    public void testAbilityCategoryToAbilityCategoryDto() {
        AbilityCategory abilityCategory = new AbilityCategory(1233L, "name", new AbilityCategory(1L, "test", null));
        AbilityCategoryDto dto = AbilityCategoryDto.createFullDto(abilityCategory);

        assertEqual(abilityCategory, dto);
    }

    public static void assertEqual(AbilityCategory abilityCategory, AbilityCategoryDto dto) {
        assertEquals(abilityCategory.getId(), dto.getId());
        assertEquals(abilityCategory.getName(), dto.getName());
        assertEquals(abilityCategory.getParentAbilityCategory(), dto.getParentAbilityCategory());
    }

    @Test
    public void testAbilityCategoryDtoToAbilityCategory() {
        AbilityCategoryDto dto = new AbilityCategoryDto(1234L, "name", new AbilityCategory(2L, "test", null));
        AbilityCategory category = AbilityCategoryDto.createAbilityCategory(dto);

        assertEqual(dto, category);
    }

    public static void assertEqual(AbilityCategoryDto dto, AbilityCategory abilityCategory) {
        assertEquals(dto.getId(), abilityCategory.getId());
        assertEquals(dto.getName(), abilityCategory.getName());
        assertEquals(dto.getParentAbilityCategory(), abilityCategory.getParentAbilityCategory());
    }
}
