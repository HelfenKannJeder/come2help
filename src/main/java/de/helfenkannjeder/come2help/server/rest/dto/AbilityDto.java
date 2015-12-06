package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Ability;
import de.helfenkannjeder.come2help.server.domain.AbilityCategory;
import org.hibernate.validator.constraints.NotEmpty;

public class AbilityDto {

    private Long id;

    @NotEmpty(message = "not.empty")
    private String name;

    private String description;

    private AbilityCategory abilityCategory;

    public AbilityDto() {
    }

    public AbilityDto(Long id, String name, String description, AbilityCategory abilityCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.abilityCategory = abilityCategory;
    }

    public static AbilityDto createFullDto(Ability ability) {
        return new AbilityDto(ability.getId(), ability.getName(), ability.getDescription(), ability.getAbilityCategory());
    }

    public static Ability createAbility(AbilityDto dto) {
        return new Ability(dto.id, dto.name, dto.description, dto.abilityCategory);
    }

    public Long getId() {
        return id;
    }

    public AbilityDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AbilityDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AbilityDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public AbilityCategory getAbilityCategory() {
        return abilityCategory;
    }

    public AbilityDto setAbilityCategory(AbilityCategory abilityCategory) {
        this.abilityCategory = abilityCategory;
        return this;
    }
}
