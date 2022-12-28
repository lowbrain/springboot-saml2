package com.example.saml2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.saml2.provider.service.metadata.OpenSamlMetadataResolver;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrations;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;
import org.springframework.security.saml2.provider.service.web.authentication.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Saml2Configuration {

    @Value("${saml2.config.location}")
	private String metaDataLocation;

    @Value("${saml2.config.id}")
	private String registrationId;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        DefaultRelyingPartyRegistrationResolver relyingPartyRegistrationResolver 
            = new DefaultRelyingPartyRegistrationResolver(this.relyingPartyRegistrations());
        Saml2MetadataFilter filter 
            = new Saml2MetadataFilter(relyingPartyRegistrationResolver, new OpenSamlMetadataResolver());
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()
            )
            .addFilterBefore(filter, Saml2WebSsoAuthenticationFilter.class)
            .saml2Login();
        return http.build();
    }

    @Bean
    public RelyingPartyRegistrationRepository relyingPartyRegistrations() {
        RelyingPartyRegistration registration = RelyingPartyRegistrations
                .fromMetadataLocation(metaDataLocation)
                .registrationId(registrationId)
                .build();
        return new InMemoryRelyingPartyRegistrationRepository(registration);
    }
}
