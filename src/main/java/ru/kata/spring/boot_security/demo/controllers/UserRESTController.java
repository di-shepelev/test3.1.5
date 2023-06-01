package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserServise;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserRESTController {

    private UserServise userServise;

    @Autowired
    public UserRESTController(UserServise userServise) {
        this.userServise = userServise;
    }

    @GetMapping()
    public User getUser(Principal principal) {
        return (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
    }

}
