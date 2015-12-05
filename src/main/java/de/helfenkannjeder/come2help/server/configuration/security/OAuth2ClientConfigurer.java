package de.helfenkannjeder.come2help.server.configuration.security;

import de.helfenkannjeder.come2help.server.configuration.security.jwt.JwtCreatingAuthenticationSuccessHandler;
import de.helfenkannjeder.come2help.server.configuration.security.jwt.StatelessJwtAuthenticationFilter;
import java.util.Arrays;
import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

@EnableOAuth2Client
@Configuration
public class OAuth2ClientConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    /**
     * Configure HttpSecurity. This includes:<br>
     * - resources requiring authorized <br>
     * - resources that are free to access <br>
     * - csrf token mapping <br>
     * - construction of the security filter chain
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable().headers().frameOptions().disable().and()
                .antMatcher("/**").authorizeRequests()
                /**/.antMatchers("/abilities", "/login/**", "/logoutWorked").permitAll()
                /**/.anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint()).and()
                .addFilterBefore(statelessJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    }

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        filter.setFilters(Arrays.asList(
                createSsoFilter(facebook(), "/login/facebook"),
                createSsoFilter(google(), "/login/google"))
        );
        return filter;
    }

    private OAuth2ClientAuthenticationProcessingFilter createSsoFilter(ClientResourceDetails clientDetails, String path) {
        OAuth2ClientAuthenticationProcessingFilter ssoFilter = new OAuth2ClientAuthenticationProcessingFilter(path);
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(clientDetails.getClient(), oAuth2ClientContext);
        ssoFilter.setRestTemplate(restTemplate);
        ssoFilter.setTokenServices(new UserInfoTokenServices(clientDetails.getResource().getUserInfoUri(), clientDetails.getClient().getClientId()));
        ssoFilter.setAuthenticationSuccessHandler(getJwtCreatingAuthenticationSuccessHandler());
        return ssoFilter;
    }

    @Bean
    protected StatelessJwtAuthenticationFilter statelessJwtAuthenticationFilter() {
        return new StatelessJwtAuthenticationFilter();
    }

//    @Bean
//    protected JwtFilter jwtFilter() {
//        return new JwtFilter();
//    }
    @Bean // handles the redirect to facebook
    public FilterRegistrationBean oAuth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    @ConfigurationProperties("facebook")
    protected ClientResourceDetails facebook() {
        return new ClientResourceDetails();
    }

    @Bean
    @ConfigurationProperties("google")
    protected ClientResourceDetails google() {
        return new ClientResourceDetails();
    }

    @Bean
    protected AuthenticationSuccessHandler getJwtCreatingAuthenticationSuccessHandler() {
        return new JwtCreatingAuthenticationSuccessHandler();
    }
}
