package de.helfenkannjeder.come2help.server.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Volunteer extends AbstractVersionedAuditable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    private List<Ability> abilities = Collections.emptyList();

    public Volunteer() {
    }

    public Volunteer(Long id, User user, List<Ability> abilities) {
        this.id = id;
        this.user = user;
        this.abilities = abilities;
    }

    public void update(Volunteer v) {
        this.abilities = v.abilities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.user);
        hash = 67 * hash + Objects.hashCode(this.abilities);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Volunteer other = (Volunteer) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.abilities, other.abilities)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Volunteer{" + "id=" + id + ", user=" + user + ", abilities=" + abilities + '}';
    }

}
