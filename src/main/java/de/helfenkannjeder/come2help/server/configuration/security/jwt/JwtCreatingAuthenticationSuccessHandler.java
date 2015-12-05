package de.helfenkannjeder.come2help.server.configuration.security.jwt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtCreatingAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtAuthenticationService tokenService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        String jwtToken = tokenService.getAuthenticationToken(userDetails);

        response.setHeader("Authorization", jwtToken);
        response.setStatus(HttpStatus.OK.value());
    }
}
