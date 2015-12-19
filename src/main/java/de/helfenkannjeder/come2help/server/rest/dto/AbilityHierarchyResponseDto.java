package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Ability;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Collections;
import java.util.List;

public class AbilityHierarchyResponseDto {

    private Long id;

    @NotEmpty(message = "not.empty")
    private String name;

    private String description;

    private List<AbilityHierarchyResponseDto> childAbilities = Collections.emptyList();

    private boolean isSelectable = true;

    private boolean isCategory = false;

    public AbilityHierarchyResponseDto() {
    }

    public AbilityHierarchyResponseDto(Long id, String name, String description, List<AbilityHierarchyResponseDto> childAbilities, boolean isSelectable, boolean isCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.childAbilities = childAbilities;
        this.isSelectable = isSelectable;
        this.isCategory = isCategory;
    }

    public static AbilityHierarchyResponseDto createFullDto(Ability ability) {
        List<AbilityHierarchyResponseDto> childAbilities = Collections.emptyList();
        ability.getChildAbilities().forEach(childAbility -> childAbilities.add(createFullDto(childAbility)));
        return new AbilityHierarchyResponseDto(ability.getId(), ability.getName(), ability.getDescription(), childAbilities, ability.isSelectable(), ability.isCategory());
    }

    public Long getId() {
        return id;
    }

    public AbilityHierarchyResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AbilityHierarchyResponseDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AbilityHierarchyResponseDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<AbilityHierarchyResponseDto> getChildAbilities() {
        return childAbilities;
    }

    public AbilityHierarchyResponseDto setChildAbilities(List<AbilityHierarchyResponseDto> childAbilities) {
        this.childAbilities = childAbilities;
        return this;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public AbilityHierarchyResponseDto setSelectable(boolean selectable) {
        isSelectable = selectable;
        return this;
    }

    public boolean isCategory() {
        return isCategory;
    }

    public AbilityHierarchyResponseDto setCategory(boolean category) {
        isCategory = category;
        return this;
    }
}
