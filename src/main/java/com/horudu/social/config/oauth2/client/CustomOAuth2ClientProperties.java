package com.horudu.social.config.oauth2.client;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "social.oauth2.client")
public class CustomOAuth2ClientProperties {

    /**
     * OAuth provider details.
     */
    private final Map<String, OAuth2ClientProperties.Provider> provider = new HashMap<>();

    /**
     * OAuth client registrations.
     */
    private final Map<String, OAuth2ClientProperties.Registration> registration = new HashMap<>();

    public Map<String, OAuth2ClientProperties.Provider> getProvider() {
        return this.provider;
    }

    public Map<String, OAuth2ClientProperties.Registration> getRegistration() {
        return this.registration;
    }

    @PostConstruct
    public void validate() {
        this.getRegistration().values().forEach(this::validateRegistration);
    }

    private void validateRegistration(OAuth2ClientProperties.Registration registration) {
        if (!StringUtils.hasText(registration.getClientId())) {
            throw new IllegalStateException("Client id must not be empty.");
        }
    }
}
