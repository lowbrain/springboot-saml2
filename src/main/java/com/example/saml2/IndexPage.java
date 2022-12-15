package com.example.saml2;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexPage {
    
    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal Saml2AuthenticatedPrincipal principal) {
		String emailAddress = principal.getFirstAttribute("email");
		model.addAttribute("emailAddress", emailAddress);
		model.addAttribute("userAttributes", principal.getAttributes());
		return "index";
	}
}
