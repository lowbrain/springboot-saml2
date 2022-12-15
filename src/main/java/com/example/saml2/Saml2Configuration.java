package com.example.saml2;

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
                .fromMetadataLocation("https://login.microsoftonline.com/0c8961ee-42a9-471b-9f8d-b002cd852be5/federationmetadata/2007-06/federationmetadata.xml?appid=c4f94dac-28ba-498e-85cd-75f142924f13")
                .registrationId("sample")
                .build();
        return new InMemoryRelyingPartyRegistrationRepository(registration);
    }
}
