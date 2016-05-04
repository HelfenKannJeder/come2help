package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Ability;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbilityResponseDto {

    private Long id;

    @NotEmpty(message = "not.empty")
    private String name;

    private String description;

    private Long parentAbilityId;

    private List<AbilityResponseDto> childAbilities = Collections.emptyList();

    private boolean isSelectable = true;

    private boolean isCategory = false;

    public AbilityResponseDto() {
    }

    public AbilityResponseDto(Long id, String name, String description, Long parentAbilityId, List<AbilityResponseDto> childAbilities, boolean isSelectable, boolean isCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentAbilityId = parentAbilityId;
        this.childAbilities = childAbilities;
        this.isSelectable = isSelectable;
        this.isCategory = isCategory;
    }

    public static AbilityResponseDto createFullDto(Ability ability) {
        List<AbilityResponseDto> childAbilities = new ArrayList<>();
        ability.getChildAbilities().forEach(childAbility -> childAbilities.add(createFullDto(childAbility)));
        return new AbilityResponseDto(ability.getId(), ability.getName(), ability.getDescription(), ability.getParentAbilityId(), childAbilities, ability.isSelectable(), ability.isCategory());
    }

    public Long getId() {
        return id;
    }

    public AbilityResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AbilityResponseDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AbilityResponseDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<AbilityResponseDto> getChildAbilities() {
        return childAbilities;
    }

    public AbilityResponseDto setChildAbilities(List<AbilityResponseDto> childAbilities) {
        this.childAbilities = childAbilities;
        return this;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public AbilityResponseDto setSelectable(boolean selectable) {
        isSelectable = selectable;
        return this;
    }

    public boolean isCategory() {
        return isCategory;
    }

    public AbilityResponseDto setCategory(boolean category) {
        isCategory = category;
        return this;
    }

    public Long getParentAbilityId() {
        return parentAbilityId;
    }

    public void setParentAbilityId(Long parentAbilityId) {
        this.parentAbilityId = parentAbilityId;
    }
}
