package de.helfenkannjeder.come2help.server.security.jwt;

import org.springframework.stereotype.Component;

@Component
public class HelfenkannjederSuccessHandler extends JwtCreatingAuthenticationSuccessHandler {

    @Override
    protected String providerIdentifier() {
        return "google";
    }

    @Override
    protected String surnameField() {
        return "family_name";
    }

    @Override
    protected String givenNameField() {
        return "given_name";
    }

    @Override
    protected String emailField() {
        return "email";
    }

    @Override
    protected String idField() {
        return "id";
    }
}
