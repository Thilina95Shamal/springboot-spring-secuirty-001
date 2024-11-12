package com.example.proj.controller.csrftokengen;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfTokenGeneratorController {

    @GetMapping("/csrf-token")
    // Through HttpServletRequest we can get the csrfToken
    public CsrfToken getCsrfToken(HttpServletRequest httpServletRequest){
        // Here the paramater should be pass that is coming from the element property name of csrf token <input name="_csrf"
        return (CsrfToken) httpServletRequest.getAttribute("_csrf");
    }
}
