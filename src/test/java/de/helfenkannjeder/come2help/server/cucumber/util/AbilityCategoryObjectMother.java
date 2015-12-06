package de.helfenkannjeder.come2help.server.cucumber.util;

import de.helfenkannjeder.come2help.server.domain.AbilityCategory;
import de.helfenkannjeder.come2help.server.rest.dto.AbilityCategoryDto;

public class AbilityCategoryObjectMother {

    public static AbilityCategoryDto anyValidAbilityCategory() {
        return new AbilityCategoryDto()
                .setName("superman power")
                .setParentAbilityCategory(new AbilityCategory(2L, "test", null));
    }

    public static AbilityCategoryDto anyInvalidAbilityCategory() {
        return anyValidAbilityCategory().setName(null);
    }
}
