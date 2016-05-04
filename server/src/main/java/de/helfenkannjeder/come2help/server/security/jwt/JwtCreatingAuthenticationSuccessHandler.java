package de.helfenkannjeder.come2help.server.security.jwt;

import de.helfenkannjeder.come2help.server.domain.User;
import de.helfenkannjeder.come2help.server.security.Authorities;
import de.helfenkannjeder.come2help.server.security.UserAuthentication;
import de.helfenkannjeder.come2help.server.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public abstract class JwtCreatingAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtAuthenticationService tokenService;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HashMap<String, String> userDetailsMap = (HashMap<String, String>) ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();

        User dbUser = userService.getUserIfExists(providerIdentifier(), getExternalId(userDetailsMap));
        UserAuthentication user;
        if (dbUser == null) {
            user = getUserAuthentication(userDetailsMap);
        } else {
            user = dbUser.createUserAuthentication();
        }

        String jwtToken = tokenService.getAuthenticationToken(user);

        response.setHeader("Authorization", jwtToken);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }

    private String getExternalId(HashMap<String, String> userDetailsMap) {
        return userDetailsMap.get(idField());
    }

    private UserAuthentication getUserAuthentication(HashMap<String, String> userDetailsMap) {
        String externalId = getExternalId(userDetailsMap);
        String email = userDetailsMap.get(emailField());
        String givenName = userDetailsMap.get(givenNameField());
        String surname = userDetailsMap.get(surnameField());

        return new UserAuthentication(providerIdentifier(), externalId, givenName, surname, email, Authorities.USER, Authorities.GUEST);
    }

    protected abstract String providerIdentifier();

    protected abstract String surnameField();

    protected abstract String givenNameField();

    protected abstract String emailField();

    protected abstract String idField();
}
