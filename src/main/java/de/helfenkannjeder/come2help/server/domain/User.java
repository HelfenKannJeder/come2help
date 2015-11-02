package de.helfenkannjeder.come2help.server.domain;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class User extends AbstractVersionedAuditable {

    @Id
    @GeneratedValue
    private Long id = null;

    @Email
    private String email;

    @NotNull
    private String givenName;

    @NotNull
    private String surname;

    @OneToOne
    @JoinColumn(name="ADDRESS_ID")
    private Address address;

    private String phone;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private boolean isAdult = false;

    private boolean isBonusProgramAccepted = false;

    @NotNull
    private String password;

    private String insurance;

    @ManyToMany
    @JoinTable( name="USER_ABILITY",
            joinColumns={@JoinColumn(name="ABILITY_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")})
    private List<Ability> abilities;

    private int maxRadiusOfAction;

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setIsAdult(boolean isAdult) {
        this.isAdult = isAdult;
    }

    public boolean isBonusProgramAccepted() {
        return isBonusProgramAccepted;
    }

    public void setIsBonusProgramAccepted(boolean isBonusProgramAccepted) {
        this.isBonusProgramAccepted = isBonusProgramAccepted;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public int getMaxRadiusOfAction() {
        return maxRadiusOfAction;
    }

    public void setMaxRadiusOfAction(int maxRadiusOfAction) {
        this.maxRadiusOfAction = maxRadiusOfAction;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (isAdult != user.isAdult) return false;
        if (isBonusProgramAccepted != user.isBonusProgramAccepted) return false;
        if (maxRadiusOfAction != user.maxRadiusOfAction) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (givenName != null ? !givenName.equals(user.givenName) : user.givenName != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (address != null ? !address.equals(user.address) : user.address != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(user.dateOfBirth) : user.dateOfBirth != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (insurance != null ? !insurance.equals(user.insurance) : user.insurance != null) return false;
        return !(abilities != null ? !abilities.equals(user.abilities) : user.abilities != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (givenName != null ? givenName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (isAdult ? 1 : 0);
        result = 31 * result + (isBonusProgramAccepted ? 1 : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (insurance != null ? insurance.hashCode() : 0);
        result = 31 * result + (abilities != null ? abilities.hashCode() : 0);
        result = 31 * result + maxRadiusOfAction;
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surname='" + surname + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", isAdult=" + isAdult +
                ", isBonusProgramAccepted=" + isBonusProgramAccepted +
                ", password='" + password + '\'' +
                ", insurance='" + insurance + '\'' +
                ", abilities=" + abilities +
                ", maxRadiusOfAction=" + maxRadiusOfAction +
                '}';
    }
}
