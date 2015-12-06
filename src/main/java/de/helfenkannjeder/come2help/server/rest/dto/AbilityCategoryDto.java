package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.AbilityCategory;
import org.hibernate.validator.constraints.NotEmpty;

public class AbilityCategoryDto {

    private Long id;

    @NotEmpty(message = "not.empty")
    private String name;

    private AbilityCategory parentAbilityCategory;

    public AbilityCategoryDto() {
    }

    public AbilityCategoryDto(Long id, String name, AbilityCategory parentAbilityCategory) {
        this.id = id;
        this.name = name;
        this.parentAbilityCategory = parentAbilityCategory;
    }

    public static AbilityCategoryDto createFullDto(AbilityCategory abilityCategory) {
        return new AbilityCategoryDto(abilityCategory.getId(), abilityCategory.getName(), abilityCategory.getParentAbilityCategory());
    }

    public static AbilityCategory createAbilityCategory(AbilityCategoryDto dto) {
        return new AbilityCategory(dto.id, dto.name, dto.parentAbilityCategory);
    }

    public Long getId() {
        return id;
    }

    public AbilityCategoryDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AbilityCategoryDto setName(String name) {
        this.name = name;
        return this;
    }

    public AbilityCategory getParentAbilityCategory() {
        return parentAbilityCategory;
    }

    public AbilityCategoryDto setParentAbilityCategory(AbilityCategory parentAbilityCategory) {
        this.parentAbilityCategory = parentAbilityCategory;
        return this;
    }
}
