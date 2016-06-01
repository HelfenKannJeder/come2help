package de.helfenkannjeder.come2help.server.configuration.security;

import de.helfenkannjeder.come2help.server.security.jwt.FacebookSuccessHandler;
import de.helfenkannjeder.come2help.server.security.jwt.GoogleSuccessHandler;
import de.helfenkannjeder.come2help.server.security.jwt.HelfenkannjederSuccessHandler;
import de.helfenkannjeder.come2help.server.security.jwt.StatelessJwtAuthenticationFilter;
import java.util.Arrays;
import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

@Configuration
public class OAuth2ClientConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private MappingJackson2HttpMessageConverter jsonMessageConverter;

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
                .antMatchers("/login/**").permitAll()
                .antMatchers("/abilities/**").permitAll()
                .antMatchers("/jsondoc/**").permitAll()
                .antMatchers("/jsondoc-ui.html").permitAll()
                .antMatchers("/webjars/jsondoc-ui-webjar/**").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint()).and();

        httpSecurity.addFilterBefore(statelessJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(createOAuth2Filter(), BasicAuthenticationFilter.class);
    }

    private Filter createOAuth2Filter() {
        CompositeFilter filter = new CompositeFilter();
        filter.setFilters(Arrays.asList(
                createOAuth2Filter(facebook(), facebookSuccessHandler(), "/login/facebook"),
                createOAuth2Filter(google(), googleSuccessHandler(), "/login/google"),
                createOAuth2Filter(helfenkannjeder(), helfenkannjederSuccessHandler(), "/login/helfenkannjeder"))
        );
        return filter;
    }

    private AbstractAuthenticationProcessingFilter createOAuth2Filter(ClientResourceDetails clientDetails, AuthenticationSuccessHandler successHandler, String path) {
        CustomOAuthAuthenticationProcessingFilter oauthFilter = new CustomOAuthAuthenticationProcessingFilter(path, clientDetails, jsonMessageConverter);
        oauthFilter.setAuthenticationSuccessHandler(successHandler);
        return oauthFilter;
    }

    @Bean
    protected StatelessJwtAuthenticationFilter statelessJwtAuthenticationFilter() {
        return new StatelessJwtAuthenticationFilter();
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
    @ConfigurationProperties("helfenkannjeder")
    protected ClientResourceDetails helfenkannjeder() {
        return new ClientResourceDetails();
    }

    @Bean
    protected AuthenticationSuccessHandler facebookSuccessHandler() {
        return new FacebookSuccessHandler();
    }

    @Bean
    protected AuthenticationSuccessHandler googleSuccessHandler() {
        return new GoogleSuccessHandler();
    }

    @Bean
    protected AuthenticationSuccessHandler helfenkannjederSuccessHandler() {
        return new HelfenkannjederSuccessHandler();
    }
}
