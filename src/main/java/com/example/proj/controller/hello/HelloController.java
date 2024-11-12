package com.example.proj.controller.hello;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    // Through HttpServletRequest we can get the session Id
    public String greet(HttpServletRequest request){
        return "Welcome Project Sample with SessionId : " + request.getSession().getId();
    }

}
