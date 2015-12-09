package de.helfenkannjeder.come2help.server.rest.dto;

import de.helfenkannjeder.come2help.server.domain.Ability;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.List;

public class AbilityDto {

    private Long id;

    @NotEmpty(message = "not.empty")
    private String name;

    private String description;

    private Ability parentAbility;

    private boolean isSelectable = true;

    private boolean isCategory = false;

    public AbilityDto() {
    }

    public AbilityDto(Long id, String name, String description, Ability parentAbility, boolean isSelectable, boolean isCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentAbility = parentAbility;
        this.isSelectable = isSelectable;
        this.isCategory = isCategory;
    }

    public static AbilityDto createFullDto(Ability ability) {
        return new AbilityDto(ability.getId(), ability.getName(), ability.getDescription(), ability.getParentAbility(), ability.isSelectable(), ability.isCategory());
    }

    public static Ability createAbility(AbilityDto dto) {
        return new Ability(dto.id, dto.name, dto.description, dto.parentAbility, dto.isSelectable, dto.isCategory);
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

    public Ability getParentAbility() {
        return parentAbility;
    }

    public AbilityDto setParentAbility(Ability parentAbility) {
        this.parentAbility = parentAbility;
        return this;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public void setSelectable(boolean selectable) {
        isSelectable = selectable;
    }

    public boolean isCategory() {
        return isCategory;
    }

    public void setCategory(boolean category) {
        isCategory = category;
    }
}
