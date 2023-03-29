package br.com.fiap.mypets.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthProperties {

    @Value("${auth.jwt.issuer}")
    private String issuer;

    @Value("${auth.jwt.secret}")
    private String secret;

    @Value("${auth.jwt.audience}")
    private String audience;

    @Value("${auth.jwt.ttl-in-seconds}")
    private Long timeToLiveInSeconds;

    public String getIssuer() {
        return issuer;
    }

    public String getSecret() {
        return secret;
    }

    public String getAudience() {
        return audience;
    }

    public Long getTimeToLiveInSeconds() {
        return timeToLiveInSeconds;
    }
}
