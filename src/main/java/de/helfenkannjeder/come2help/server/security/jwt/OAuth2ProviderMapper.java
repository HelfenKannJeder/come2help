package de.helfenkannjeder.come2help.server.security.jwt;

import de.helfenkannjeder.come2help.server.security.UserAuthentication;
import java.util.HashMap;

public abstract class OAuth2ProviderMapper {

    public abstract UserAuthentication getUserAuthenticationData(HashMap<String, String> userDetailsMap);
}
