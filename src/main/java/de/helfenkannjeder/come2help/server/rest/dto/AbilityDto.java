package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Ability;
import org.hibernate.validator.constraints.NotEmpty;

public class AbilityDto {

    private Long id;

    @NotEmpty(message = "not.empty")
    private String name;

    private String description;

    private Long parentAbilityId;

    private boolean isSelectable = true;

    private boolean isCategory = false;

    public AbilityDto() {
    }

    public AbilityDto(Long id, String name, String description, Long parentAbilityId, boolean isSelectable, boolean isCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentAbilityId = parentAbilityId;
        this.isSelectable = isSelectable;
        this.isCategory = isCategory;
    }

    public static AbilityDto createFullDto(Ability ability) {
        return new AbilityDto(ability.getId(), ability.getName(), ability.getDescription(), ability.getParentAbilityId(), ability.isSelectable(), ability.isCategory());
    }

    public static Ability createAbility(AbilityDto dto) {
        return new Ability(dto.id, dto.name, dto.description, null, dto.isSelectable, dto.isCategory); //TODO match parent
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

    public Long getParentAbilityId() {
        return parentAbilityId;
    }

    public AbilityDto setParentAbilityId(Long parentAbilityId) {
        this.parentAbilityId = parentAbilityId;
        return this;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public AbilityDto setSelectable(boolean selectable) {
        isSelectable = selectable;
        return this;
    }

    public boolean isCategory() {
        return isCategory;
    }

    public AbilityDto setCategory(boolean category) {
        isCategory = category;
        return this;
    }
}
