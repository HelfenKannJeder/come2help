package de.helfenkannjeder.come2help.server.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Ability extends AbstractVersionedAuditable {

    @Id
    @GeneratedValue
    private Long id = null;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "abilities")
    private List<Volunteer> volunteers = Collections.emptyList();

    @ManyToOne
    @JoinColumn(name = "PARENT_ABILITY_ID")
    private Ability parentAbility;

    @OneToMany(mappedBy = "parentAbility")
    private List<Ability> childAbilities = Collections.emptyList();

    private boolean isSelectable = true;

    private boolean isCategory = false;

    public Ability() {
    }

    public Ability(Long id, String name, String description, Ability parentAbility, boolean isSelectable, boolean isCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentAbility = parentAbility;
        this.isSelectable = isSelectable;
        this.isCategory = isCategory;
    }

    public void update(Ability ability) {
        this.name = ability.name;
        this.description = ability.description;
        this.parentAbility = ability.parentAbility;
        this.childAbilities = ability.childAbilities;
        this.isCategory = ability.isCategory();
        this.isSelectable = ability.isSelectable();
        checkCategoryAndChildrenConsistency();
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

    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }

    public Ability getParentAbility() {
        return parentAbility;
    }

    public void setParentAbility(Ability parentAbility) {
        this.parentAbility = parentAbility;
    }

    public List<Ability> getChildAbilities() {
        return childAbilities;
    }

    public void setChildAbilities(List<Ability> childAbilities) {
        this.childAbilities = childAbilities;
        checkCategoryAndChildrenConsistency();
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
        checkCategoryAndChildrenConsistency();
    }

    private void checkCategoryAndChildrenConsistency() {
        if (isCategory == false && childAbilities.size() > 0 ) {
            throw new RuntimeException("An ability has childAbilites but is not a category");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ability ability = (Ability) o;

        if (isSelectable != ability.isSelectable) return false;
        if (isCategory != ability.isCategory) return false;
        if (!id.equals(ability.id)) return false;
        if (!name.equals(ability.name)) return false;
        if (!description.equals(ability.description)) return false;
        if (!volunteers.equals(ability.volunteers)) return false;
        if (!parentAbility.equals(ability.parentAbility)) return false;
        return childAbilities.equals(ability.childAbilities);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + volunteers.hashCode();
        result = 31 * result + parentAbility.hashCode();
        result = 31 * result + childAbilities.hashCode();
        result = 31 * result + (isSelectable ? 1 : 0);
        result = 31 * result + (isCategory ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ability{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", volunteers=" + volunteers +
                ", parentAbility=" + parentAbility +
                ", childAbilities=" + childAbilities +
                ", isSelectable=" + isSelectable +
                ", isCategory=" + isCategory +
                '}';
    }
}
