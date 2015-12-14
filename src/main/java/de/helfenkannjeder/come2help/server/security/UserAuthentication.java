package de.helfenkannjeder.come2help.server.security;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthentication implements Authentication {

    private String internalId;
    private String authProvider;
    private String externalId;
    private String givenName;
    private String surname;
    private String email;
    private List<GrantedAuthority> grantedAuthorities = new LinkedList<>();

    public UserAuthentication() {
    }

    public UserAuthentication(String internalId, String authProvider, String externalId, String givenName, String surname, String email) {
        this.internalId = internalId;
        this.authProvider = authProvider;
        this.externalId = externalId;
        this.givenName = givenName;
        this.surname = surname;
        this.email = email;
    }

    public UserAuthentication(String authProvider, String externalId, String givenName, String surname, String email) {
        this.authProvider = authProvider;
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

    public String getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(String authProvider) {
        this.authProvider = authProvider;
    }

    public List<GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(List<GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
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
        final UserAuthentication other = (UserAuthentication) obj;
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
