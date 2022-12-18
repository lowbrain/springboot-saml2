package com.example.saml2;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthData {
    private List<Object> name;
    private List<Object> givenname;
    private List<Object> surname;
    private List<Object> emailaddress;
}
