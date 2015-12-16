package de.helfenkannjeder.come2help.server.configuration.security;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;

@Configuration
public class SessionlessOAuth2ClientConfiguration {

    @Bean
    public OAuth2ClientContextFilter oauth2ClientContextFilter() {
        return new OAuth2ClientContextFilter();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    protected AccessTokenRequest accessTokenRequest(@Value("#{request.parameterMap}") Map<String, String[]> parameters, @Value("#{request.getAttribute('currentUri')}") String currentUri) {
        DefaultAccessTokenRequest request = new DefaultAccessTokenRequest(parameters);
        request.setCurrentUri(currentUri);
        return request;
    }

    @Configuration
    protected static class OAuth2ClientContextConfiguration {

        @Resource
        @Qualifier("accessTokenRequest")
        private AccessTokenRequest accessTokenRequest;

        @Bean
//        @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
        public OAuth2ClientContext oauth2ClientContext() {
            return new DefaultOAuth2ClientContext(accessTokenRequest);
        }
    }
}
