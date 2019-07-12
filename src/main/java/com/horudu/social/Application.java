package com.horudu.social;

import com.horudu.social.config.oauth2.client.CustomOAuth2ClientProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CustomOAuth2ClientProperties.class)
public class Application {
    private static final String PROPERTIES = "spring.config.location=classpath:/social.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .properties(PROPERTIES)
                .run(args);
        //SpringApplication.run(Application.class, args);
    }

}
