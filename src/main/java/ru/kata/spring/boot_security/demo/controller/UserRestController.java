package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRestController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);
        return user;
    }
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user, @RequestParam Long[] roleIds) {
        User existUser = userService.getUserById(id);

        existUser.setName(user.getName()); // дать новые данные
        existUser.setLastName(user.getLastName());
        existUser.setAge(user.getAge());
        existUser.setEmail(user.getEmail());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existUser.setPassword(passwordEncoder.encode(user.getPassword())); //обновление пароля
        }

        existUser.setRoles(roleService.getRolesByIds(roleIds));
        userService.update(existUser);
        return existUser;
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<User> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles(); // для получения ролей
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }
}
