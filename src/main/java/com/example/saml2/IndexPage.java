package com.example.saml2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexPage {

  @Value("${saml2.return.url}")
	private String retUrl;

  @RequestMapping("/")
  public String index(Model model, @AuthenticationPrincipal Saml2AuthenticatedPrincipal principal) {
    AuthData data = AuthData.builder()
        .name(principal.getAttribute("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name"))
        .emailaddress(principal.getAttribute("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress"))
        .givenname(principal.getAttribute("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/givenname"))
        .surname(principal.getAttribute("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/surname"))
        .build();
    model.addAttribute("name", data.getName());
    model.addAttribute("authInfo", data);
    model.addAttribute("retUrl", retUrl);
    return "index";
  }
}
