package de.helfenkannjeder.come2help.server.domain;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;

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

    private String phone;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private boolean isAdult = false;

    private boolean isBonusProgramAccepted = false;

    @NotNull
    private String password;

    private String insurance;

    @ManyToMany
    @JoinTable(name = "USER_ABILITY",
            joinColumns = {
                @JoinColumn(name = "ABILITY_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {
                @JoinColumn(name = "USER_ID", referencedColumnName = "ID")})
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
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.email);
        hash = 97 * hash + Objects.hashCode(this.givenName);
        hash = 97 * hash + Objects.hashCode(this.surname);
        hash = 97 * hash + Objects.hashCode(this.phone);
        hash = 97 * hash + Objects.hashCode(this.dateOfBirth);
        hash = 97 * hash + (this.isAdult ? 1 : 0);
        hash = 97 * hash + (this.isBonusProgramAccepted ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.password);
        hash = 97 * hash + Objects.hashCode(this.insurance);
        hash = 97 * hash + Objects.hashCode(this.abilities);
        hash = 97 * hash + this.maxRadiusOfAction;
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
        final User other = (User) obj;
        if (this.isAdult != other.isAdult) {
            return false;
        }
        if (this.isBonusProgramAccepted != other.isBonusProgramAccepted) {
            return false;
        }
        if (this.maxRadiusOfAction != other.maxRadiusOfAction) {
            return false;
        }
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
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.insurance, other.insurance)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dateOfBirth, other.dateOfBirth)) {
            return false;
        }
        if (!Objects.equals(this.abilities, other.abilities)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", givenName=" + givenName + ", surname=" + surname + ", phone=" + phone + ", dateOfBirth=" + dateOfBirth + ", isAdult=" + isAdult + ", isBonusProgramAccepted=" + isBonusProgramAccepted + ", password=" + password + ", insurance=" + insurance + ", abilities=" + abilities + ", maxRadiusOfAction=" + maxRadiusOfAction + '}';
    }
}
