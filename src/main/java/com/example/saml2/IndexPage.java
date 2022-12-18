package com.example.saml2;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexPage {

  @RequestMapping("/")
  public AuthData index(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal) {
    AuthData data = AuthData.builder()
        .name(principal.getAttribute("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name"))
        .emailaddress(principal.getAttribute("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress"))
        .givenname(principal.getAttribute("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/givenname"))
        .surname(principal.getAttribute("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/surname"))
        .build();
    return data;
  }
  
}
