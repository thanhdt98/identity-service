package com.thanhxv.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
/**
 * explain @EnableMethodSecurity for enable method authorization
 */
@EnableMethodSecurity
public class SecurityConfig {
    private final String[] PUBLIC_ENDPOINTS = {
            "/users",
            "/auth/token", "auth/introspect"
    };

    @Value("${jwt.signerKey}")
    private String signerKey;


    /**
     * Config for spring security 6. spring boot 3
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf(Customizer.withDefaults())
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults());

        httpSecurity.authorizeHttpRequests(requests ->
                requests.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                        /**
                         * explain SCOPE_ADMIN co the custom SCOPE_ bang config .jwtAuthenticationConverter(jwtAuthenticationConverter())
                         * neu custom thanh ROLE_ thi co the dung .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN") vi Spring se tu tim dc role ROLE_ADMIN
                         */
                        /**
                         * comment due to use Method Authorization
                         */
//                        .requestMatchers(HttpMethod.GET, "/users").hasRole(Role.ADMIN.name())
//                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/users").hasAnyAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated());

        /**
         * note
         * jwtConfigurer.jwkSetUri
         * neu cau hinh voi resource server thu 3 thi chi can cung cap uri
         * ma o day khong phai authen voi Identity Provider (IDP) ben ngoai chi muon authen cho jwt chung ta gen
         * nen o day chi can jwt decoder giup decode jwt token
         */
        /**
         * explain
         * 1. khi config oauth2ResourceServer => muon dang ky 1 authentication provider support jwt
         *      co nghia la se thuc hien authentication
         * 2. khi thuc hien validate authentication can 1 jwt decoder de biet token co hop le khong
         * 3. SCOPE_ co the custom thanh ROLE_ bang config .jwtAuthenticationConverter(jwtAuthenticationConverter())
         */
        httpSecurity.oauth2ResourceServer(oauth2ResourceServer ->
                oauth2ResourceServer.jwt(jwtConfigurer -> jwtConfigurer
                        .decoder(jwtDecoder())
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
        );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    /**
     * explain
     * custom claim "scope" or SCOPE_
     * @return
     */
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        /**
         * explain
         * HS512 la thuat toan khi gen token
         */
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");

        NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
        return nimbusJwtDecoder;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}

