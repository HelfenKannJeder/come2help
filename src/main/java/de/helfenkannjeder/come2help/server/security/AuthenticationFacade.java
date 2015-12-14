package de.helfenkannjeder.come2help.server.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public UserAuthentication getAuthentication() {
        return (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
