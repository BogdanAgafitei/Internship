package com.seedon.SeedOnTanda.auth.facade;

import com.seedon.SeedOnTanda.enums.roles.RoleValues;
import com.seedon.SeedOnTanda.properties.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AuthenticationFacade {


    private final RestTemplate restTemplate;

    private final AuthProperties authProperties;

    @Autowired
    public AuthenticationFacade(AuthProperties authProperties, RestTemplateBuilder restTemplateBuilder) {
        this.authProperties = authProperties;
        restTemplate = restTemplateBuilder.uriTemplateHandler(new DefaultUriBuilderFactory(authProperties.getBaseUrl())).build();
    }

    public boolean isTokenValid(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        final var response = restTemplate.exchange(authProperties.getAuthValidate(), HttpMethod.HEAD, entity, Void.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public List<RoleValues> getAuthorities(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        final var response = restTemplate.exchange("/v1/auth/session/authorities", HttpMethod.GET, entity, String[].class);
        return Arrays.stream(Objects.requireNonNull(response.getBody()))
                .map(RoleValues::mapRole)
                .toList();


    }

}