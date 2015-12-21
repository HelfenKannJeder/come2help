package de.helfenkannjeder.come2help.server.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserAuthentication implements Authentication {

    private Long internalId;
    private String authProvider;
    private String externalId;
    private String givenName;
    private String surname;
    private String email;
    private List<GrantedAuthority> grantedAuthorities = new LinkedList<>();

    public UserAuthentication() {
    }

    public UserAuthentication(Long internalId, String authProvider, String externalId, String givenName, String surname, String email, List<String> grantedAuthorities) {
        this.internalId = internalId;
        this.authProvider = authProvider;
        this.externalId = externalId;
        this.givenName = givenName;
        this.surname = surname;
        this.email = email;
        this.grantedAuthorities = grantedAuthorities.stream().map(a -> new SimpleGrantedAuthority(a)).collect(Collectors.toList());
    }

    public UserAuthentication(String authProvider, String externalId, String givenName, String surname, String email, String... authorities) {
        this(null, authProvider, externalId, givenName, surname, email, Arrays.asList(authorities));
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
        return internalId + "";
    }

    public Long getInternalId() {
        return internalId;
    }

    public void setInternalId(Long internalId) {
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
        return "UserAuthentication{" + "internalId=" + internalId + ", externalId=" + externalId + ", givenName=" + givenName + ", surname=" + surname + ", email=" + email + '}';
    }

}
