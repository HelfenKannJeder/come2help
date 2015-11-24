package de.helfenkannjeder.come2help.server.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

@Entity
public class Volunteer extends AbstractVersionedAuditable {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    @NotNull
    private String givenName;

    @NotNull
    private String surname;

    @Embedded
    @NotNull
    private Address address;

    private String phone;

    private Boolean adult;

    @ManyToMany
    private List<Ability> abilities = Collections.emptyList();

    public Volunteer() {
    }

    public Volunteer(Long id, String email, String givenName, String surname, Address address, String phone, Boolean adult, List<Ability> abilities) {
        this.id = id;
        this.email = email;
        this.givenName = givenName;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.adult = adult;
        this.abilities = abilities;
    }

    public void update(Volunteer v) {
        this.email = v.email;
        this.givenName = v.givenName;
        this.surname = v.surname;
        this.address.update(v.address);
        this.phone = v.phone;
        this.adult = v.adult;
        this.abilities = v.abilities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean isAdult() {
        return adult;
    }

    public void setAdult(Boolean isAdult) {
        this.adult = isAdult;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    @PreUpdate
    @PrePersist
    protected void updateCoordinates() {
        if (address != null) {
            address.updateCoordinates();
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.email);
        hash = 23 * hash + Objects.hashCode(this.givenName);
        hash = 23 * hash + Objects.hashCode(this.surname);
        hash = 23 * hash + Objects.hashCode(this.address);
        hash = 23 * hash + Objects.hashCode(this.phone);
        hash = 23 * hash + Objects.hashCode(this.adult);
        hash = 23 * hash + Objects.hashCode(this.abilities);
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
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.givenName, other.givenName)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.adult, other.adult)) {
            return false;
        }
        if (!Objects.equals(this.abilities, other.abilities)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Volunteer{" + "id=" + id + ", email=" + email + ", givenName=" + givenName + ", surname=" + surname + ", address=" + address + ", phone=" + phone + ", adult=" + adult + ", abilities=" + abilities + '}';
    }
}
