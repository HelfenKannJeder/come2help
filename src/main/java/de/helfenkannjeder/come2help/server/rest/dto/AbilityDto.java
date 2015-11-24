package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Ability;
import javax.validation.constraints.NotNull;

public class AbilityDto {

    private Long id;
    @NotNull
    private String name;
    private String description;

    public AbilityDto() {
    }

    public AbilityDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static AbilityDto createFullDto(Ability ability) {
        return new AbilityDto(ability.getId(), ability.getName(), ability.getDescription());
    }

    public static Ability createAbility(AbilityDto dto) {
        return new Ability(dto.id, dto.name, dto.description);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
