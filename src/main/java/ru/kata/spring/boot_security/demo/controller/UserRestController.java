package ru.kata.spring.boot_security.demo.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;



@RestController
@RequestMapping("/api/users")
public class UserRestController {

    public UserRestController() {
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }
}
