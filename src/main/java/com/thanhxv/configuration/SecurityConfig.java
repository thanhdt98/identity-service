package com.thanhxv.configuration;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
/**
 * explain @EnableMethodSecurity for enable method authorization
 */
@EnableMethodSecurity
public class SecurityConfig {
    private final String[] PUBLIC_ENDPOINTS = {"/users", "/auth/token", "auth/introspect", "auth/logout", "auth/refresh"
    };

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Autowired
    private CustomJwtDecoder jwtDecoder;

    /**
     * Config for spring security 6. spring boot 3
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.OPTIONS)
                        .permitAll())
                .authorizeHttpRequests(requests -> requests.requestMatchers(PUBLIC_ENDPOINTS)
                        .permitAll()
                /**
                 * explain SCOPE_ADMIN co the custom SCOPE_ bang config .jwtAuthenticationConverter(jwtAuthenticationConverter())
                 * neu custom thanh ROLE_ thi co the dung .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN") vi Spring se tu tim dc role ROLE_ADMIN
                 */
                /**
                 * comment due to use Method Authorization @PreAuthorization
                 */
                //                        .requestMatchers(HttpMethod.GET, "/users").hasRole(Role.ADMIN.name())
                //                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                //                        .requestMatchers(HttpMethod.GET, "/users").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest()
                .authenticated());

        /**
         * note
         * jwtConfigurer.jwkSetUri
         * neu cau hinh voi resource server thu 3 thi chi can cung cap uri
         * ma o day khong phai authenticate voi Identity Provider (IDP) ben ngoai chi muon authen cho jwt chung ta gen
         * nen o day chi can jwt decoder giup decode jwt token
         */
        /**
         * explain
         * 1. khi config oauth2ResourceServer => muon dang ky 1 authentication provider support jwt
         *      co nghia la se thuc hien authentication
         * 2. khi thuc hien validate authentication can 1 jwt decoder de biet token co hop le khong
         * 3. SCOPE_ co the custom thanh ROLE_ bang config .jwtAuthenticationConverter(jwtAuthenticationConverter())
         */
        httpSecurity.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                .jwt(jwtConfigurer -> jwtConfigurer
                        /**
                         * explain
                         * comment due to add logout feature
                         * instead of, using CustomJwtDecoder for check token invalid and has been logout
                         */
                        //                                .decoder(jwtDecoder())
                        .decoder(jwtDecoder)
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                /**
                 * explain khi authentication fail thi dieu huong di dau. o day chi can tra error message
                 * can implements AuthenticationEntryPoint interface
                 * chi dung o day nen k can tao bean
                 */
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        /**
         * explain cho phep truy cap tu nguon nao
         */
//        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        /**
         * explain UrlBasedCorsConfigurationSource de khai bao cors cho tung endpoint
         */
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        /**
         * expalin apply cors cho toan bo endpoint
         */
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    /**
     * explain
     * custom claim "scope" or SCOPE_
     * @return
     */
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        /**
         * Do co Role va Permission nen se add prefix ROLE_ cho Role khi gen token
         */
        //        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    /**
     * explain
     * comment due to add logout feature
     * instead of, using CustomJwtDecoder for check token invalid and has been logout
     * @return
     */
    //    @Bean
    //    JwtDecoder jwtDecoder() {
    //        /**
    //         * explain
    //         * HS512 la thuat toan khi gen token
    //         */
    //        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
    //
    //        NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder
    //                .withSecretKey(secretKeySpec)
    //                .macAlgorithm(MacAlgorithm.HS512)
    //                .build();
    //        return nimbusJwtDecoder;
    //    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
