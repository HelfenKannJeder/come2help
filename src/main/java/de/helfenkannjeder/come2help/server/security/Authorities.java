package de.helfenkannjeder.come2help.server.security;

public final class Authorities {

    private Authorities() {
    }

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String GUEST = "ROLE_GUEST";
    public static final String VOLUNTEER = "ROLE_VOLUNTEER";
    public static final String ORGANISATION_ADMIN = "ROLE_ORGANISATION_ADMIN";
    public static final String C2H_ADMIN = "ROLE_C2H_ADMIN";
}
