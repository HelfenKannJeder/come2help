package de.helfenkannjeder.come2help.server.domain;

import de.helfenkannjeder.come2help.server.security.Authorities;
import de.helfenkannjeder.come2help.server.security.UserAuthentication;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "admins", fetch = FetchType.EAGER)
    private Set<Organisation> organisations = new HashSet<>();

    //TODO: save adult info?
    public User() {
    }

    public User(String email, String givenName, String surname, String phone, Address address) {
        this.email = email;
        this.givenName = givenName;
        this.surname = surname;
        this.phone = phone;
        setAddress(address);
    }

    public void update(User user) {
        this.email = user.getEmail();
        this.givenName = user.getGivenName();
        this.surname = user.getSurname();
        this.phone = user.getPhone();
        setAddress(user.getAddress());
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public User setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public User setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getAuthProvider() {
        return authProvider;
    }

    public User setAuthProvider(String authProvider) {
        this.authProvider = authProvider;
        return this;
    }

    public String getExternalId() {
        return externalId;
    }

    public User setExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public User setAddress(Address address) {
        this.address = address;
        if (address != null) {
            address.updateCoordinates();
        }
        return this;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public User setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
        return this;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Set<Organisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(Set<Organisation> organisations) {
        this.organisations = organisations;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", authProvider=" + authProvider + ", externalId=" + externalId + ", email=" + email + ", emailVerified=" + emailVerified + ", givenName=" + givenName + ", surname=" + surname + ", phone=" + phone + ", address=" + address + ", volunteer=" + volunteer + '}';
    }

    public List<String> getGrantedAuthorities() {
        LinkedList<String> authorities = new LinkedList<>();
        authorities.add(Authorities.USER);

        if (this.volunteer != null) {
            authorities.add(Authorities.VOLUNTEER);
        }

        if (this.organisations != null && this.organisations.size() > 0) {
            authorities.add(Authorities.ORGANISATION_ADMIN);
        }

        return authorities;
    }

    public UserAuthentication createUserAuthentication() {
        return new UserAuthentication(getId(), getAuthProvider(), getExternalId(), getGivenName(), getSurname(), getEmail(), getGrantedAuthorities());
    }
}
