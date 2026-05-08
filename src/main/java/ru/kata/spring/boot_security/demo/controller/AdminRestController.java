package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.listUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build(); //400
        }
        return ResponseEntity.ok(user); //200
    }

    @PostMapping
    public User add(@RequestBody User user, @RequestParam Long[] roleIds) {
        user.setRoles(roleService.getRolesByIds(roleIds));
        userService.add(user);
        return user;
    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<User> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user, @RequestParam Long[] roleIds) {
        return userService.updateUser(id, user, roleIds);
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
}
