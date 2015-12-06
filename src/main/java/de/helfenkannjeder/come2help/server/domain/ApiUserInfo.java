package de.helfenkannjeder.come2help.server.domain;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class ApiUserInfo implements Authentication {

    private String internalId;
    private String externalId;
    private String givenName;
    private String surname;
    private String email;
    private List<GrantedAuthority> grantedAuthorities = new LinkedList<>();

    public ApiUserInfo() {
    }

    public ApiUserInfo(String internalId, String externalId, String givenName, String surname, String email) {
        this.internalId = internalId;
        this.externalId = externalId;
        this.givenName = givenName;
        this.surname = surname;
        this.email = email;
    }

    public ApiUserInfo(String externalId, String givenName, String surname, String email) {
        this.externalId = externalId;
        this.givenName = givenName;
        this.surname = surname;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getName() {
        return internalId;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.internalId);
        hash = 83 * hash + Objects.hashCode(this.externalId);
        hash = 83 * hash + Objects.hashCode(this.givenName);
        hash = 83 * hash + Objects.hashCode(this.surname);
        hash = 83 * hash + Objects.hashCode(this.email);
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
        final ApiUserInfo other = (ApiUserInfo) obj;
        if (!Objects.equals(this.internalId, other.internalId)) {
            return false;
        }
        if (!Objects.equals(this.externalId, other.externalId)) {
            return false;
        }
        if (!Objects.equals(this.givenName, other.givenName)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ApiUserInfo{" + "internalId=" + internalId + ", externalId=" + externalId + ", givenName=" + givenName + ", surname=" + surname + ", email=" + email + '}';
    }
}
