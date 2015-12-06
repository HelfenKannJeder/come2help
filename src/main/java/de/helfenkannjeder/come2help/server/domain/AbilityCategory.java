package de.helfenkannjeder.come2help.server.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AbilityCategory extends AbstractVersionedAuditable {

    @Id
    @GeneratedValue
    private Long id = null;

    private String name;

    @OneToMany(mappedBy = "parentAbilityCategory", cascade = CascadeType.ALL)
    private Set<AbilityCategory> childAbilityCategories = new HashSet<AbilityCategory>();

    @ManyToOne
    @JoinColumn(name = "PARENT_ABILITY_CATEGORY_ID")
    private AbilityCategory parentAbilityCategory;

    @OneToMany(mappedBy = "abilityCategory", cascade = CascadeType.ALL)
    private Set<Ability> abilities = new HashSet<Ability>();

    public AbilityCategory() {
    }

    public AbilityCategory(Long id, String name, AbilityCategory parentAbilityCategory) {
        this.id = id;
        this.name = name;
        this.parentAbilityCategory = parentAbilityCategory;
    }

    public void update(AbilityCategory abilityCategory) {
        this.name = abilityCategory.name;
        this.parentAbilityCategory = abilityCategory.parentAbilityCategory;
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

    public Set<AbilityCategory> getChildAbilityCategories() {
        return childAbilityCategories;
    }

    public void setChildAbilityCategories(Set<AbilityCategory> childAbilityCategories) {
        this.childAbilityCategories = childAbilityCategories;
    }

    public AbilityCategory getParentAbilityCategory() {
        return parentAbilityCategory;
    }

    public void setParentAbilityCategory(AbilityCategory parentAbilityCategory) {
        this.parentAbilityCategory = parentAbilityCategory;
    }

    public Set<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(Set<Ability> abilities) {
        this.abilities = abilities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbilityCategory that = (AbilityCategory) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (childAbilityCategories != null ? !childAbilityCategories.equals(that.childAbilityCategories) : that.childAbilityCategories != null)
            return false;
        if (parentAbilityCategory != null ? !parentAbilityCategory.equals(that.parentAbilityCategory) : that.parentAbilityCategory != null)
            return false;
        return !(abilities != null ? !abilities.equals(that.abilities) : that.abilities != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (childAbilityCategories != null ? childAbilityCategories.hashCode() : 0);
        result = 31 * result + (parentAbilityCategory != null ? parentAbilityCategory.hashCode() : 0);
        result = 31 * result + (abilities != null ? abilities.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AbilityCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", childAbilityCategories=" + childAbilityCategories +
                ", parentAbilityCategory=" + parentAbilityCategory +
                ", abilities=" + abilities +
                '}';
    }
}
