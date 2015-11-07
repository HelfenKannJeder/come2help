package de.helfenkannjeder.come2help.server.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Entity
public class Organisation extends AbstractVersionedAuditable {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    @OneToMany
    @JoinTable
    (
            name="ORGANISATION_ADMIN",
            joinColumns={ @JoinColumn(name="ORGANISATION_ID", referencedColumnName="ID") },
            inverseJoinColumns={ @JoinColumn(name="USER_ID", referencedColumnName="ID", unique=true) }
    )
    private Set<User> admins;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ADDRESS_ID")
    private Address address;

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

    public Set<User> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<User> admins) {
        this.admins = admins;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organisation that = (Organisation) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (admins != null ? !admins.equals(that.admins) : that.admins != null) return false;
        return !(address != null ? !address.equals(that.address) : that.address != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (admins != null ? admins.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", admins=" + admins +
                ", address=" + address +
                '}';
    }
}
