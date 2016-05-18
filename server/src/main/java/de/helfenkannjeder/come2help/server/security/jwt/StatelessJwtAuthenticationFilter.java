package de.helfenkannjeder.come2help.server.security.jwt;

import de.helfenkannjeder.come2help.server.rest.exceptionhandling.RestExceptionResolver;
import de.helfenkannjeder.come2help.server.security.Authorities;
import de.helfenkannjeder.come2help.server.security.UserAuthentication;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class StatelessJwtAuthenticationFilter extends GenericFilterBean {

    @Autowired
    private JwtAuthenticationService authenticationService;

    @Autowired
    private RestExceptionResolver exceptionResolver;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            Authentication authentication = authenticationService.getAuthentication(httpRequest);
            if (authentication == null) {
                authentication = UserAuthentication.createDummyAuthentication(Authorities.GUEST);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException | SignatureException ex) {
            exceptionResolver.resolveException(ex, HttpStatus.UNAUTHORIZED, (HttpServletResponse) response);
        } catch (MalformedJwtException ex) {
            exceptionResolver.resolveException(ex, HttpStatus.BAD_REQUEST, (HttpServletResponse) response);
        }
    }
}
