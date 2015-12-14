package de.helfenkannjeder.come2help.server.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"authProvider", "externalId"})
)
public class User extends AbstractVersionedAuditable {

    @Id
    @GeneratedValue
    private Long id = null;

    private String authProvider;
    private String externalId;

    @Column(unique = true)
    private String email;
    private Boolean emailVerified;

    private String givenName;
    private String surname;
    private String phone;
    @Embedded
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", optional = true)
    private Volunteer volunteer;

    public User() {
    }

    public User(String email, String givenName, String surname, String phone, Address address) {
        this.email = email;
        this.givenName = givenName;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
    }

    public void update(User user) {
        this.email = user.getEmail();
        this.givenName = user.getGivenName();
        this.surname = user.getSurname();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.volunteer = user.getVolunteer();
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

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(String authProvider) {
        this.authProvider = authProvider;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", authProvider=" + authProvider + ", externalId=" + externalId + ", email=" + email + ", emailVerified=" + emailVerified + ", givenName=" + givenName + ", surname=" + surname + ", phone=" + phone + ", address=" + address + ", volunteer=" + volunteer + '}';
    }
}
