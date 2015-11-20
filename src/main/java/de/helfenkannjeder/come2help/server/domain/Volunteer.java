package de.helfenkannjeder.come2help.server.domain;

import java.util.Collections;
import java.util.List;
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

    public Volunteer(Long id, String email, String givenName, String surname, Address address, String phone, boolean adult, List<Ability> abilities) {
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
}
