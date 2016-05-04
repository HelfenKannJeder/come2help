package de.helfenkannjeder.come2help.server.cucumber.util;

import de.helfenkannjeder.come2help.server.rest.dto.AbilityDto;

public class AbilityObjectMother {

    public static AbilityDto anyValidAbility() {
        return new AbilityDto()
                .setDescription("a description")
                .setName("superman power");
    }

    public static AbilityDto anyInvalidAbility() {
        return anyValidAbility().setName(null);
    }
}
