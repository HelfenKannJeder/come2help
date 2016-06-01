package de.helfenkannjeder.come2help.server.security.jwt;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public class HelfenkannjederSuccessHandler extends JwtCreatingAuthenticationSuccessHandler {
    @Override
    protected String providerIdentifier() {
        return "identifier";
    }

    @Override
    protected String surnameField() {
        return "surname";
    }

    @Override
    protected String givenNameField() {
        return "givenName";
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
