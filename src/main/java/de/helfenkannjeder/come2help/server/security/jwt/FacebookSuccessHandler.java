package de.helfenkannjeder.come2help.server.security.jwt;

import org.springframework.stereotype.Component;

@Component
public class FacebookSuccessHandler extends JwtCreatingAuthenticationSuccessHandler {

    @Override
    protected String providerIdentifier() {
        return "facebook";
    }

    @Override
    protected String surnameField() {
        return "last_name";
    }

    @Override
    protected String givenNameField() {
        return "first_name";
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
