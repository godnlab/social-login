package com.horudu.social.config;

import com.horudu.social.config.oauth2.client.CustomOAuth2ClientProperties;
import com.horudu.social.config.oauth2.client.CustomOAuth2Provider;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
public class SocialConfig {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties, CustomOAuth2ClientProperties customOAuth2ClientProperties) {

        List<ClientRegistration> registrations = oAuth2ClientProperties
                .getRegistration().keySet().stream()
                .map(client -> getRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<ClientRegistration> customRegistrations = customOAuth2ClientProperties
                .getRegistration().keySet().stream()
                .map(client -> getCustomRegistration(customOAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        registrations.stream()
                .forEachOrdered(customRegistrations::add);

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(OAuth2ClientProperties oAuth2ClientProperties, String client) {

        OAuth2ClientProperties.Registration registration = oAuth2ClientProperties.getRegistration().get(client);

        if (client.equals("google")) {
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email", "profile")
                    .build();
        }

        return null;
    }

    private ClientRegistration getCustomRegistration(CustomOAuth2ClientProperties oAuth2ClientProperties, String client) {

        OAuth2ClientProperties.Registration registration = oAuth2ClientProperties.getRegistration().get(client);

        if (client.equals("kakao")) {
            return CustomOAuth2Provider.KAKAO.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("profile", "account_email")
                    .build();
        }

        return null;
    }
}
