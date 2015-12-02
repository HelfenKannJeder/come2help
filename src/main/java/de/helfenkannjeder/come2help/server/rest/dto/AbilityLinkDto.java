package de.helfenkannjeder.come2help.server.rest.dto;

import javax.validation.constraints.NotNull;

import de.helfenkannjeder.come2help.server.domain.Ability;

public class AbilityLinkDto {

    @NotNull
    private Long id;

    private String name;

    private String description;

    public AbilityLinkDto() {
    }

    public AbilityLinkDto(Long id) {
        this.id = id;
    }

    public static AbilityLinkDto createFullDto(Ability ability) {
        return new AbilityLinkDto(ability.getId());
    }

    public static Ability createAbility(AbilityLinkDto dto) {
        return new Ability(dto.getId(), dto.getName(), dto.getDescription());
    }

    public Long getId() {
        return id;
    }

    public AbilityLinkDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AbilityLinkDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AbilityLinkDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
