package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {
    void add(User user);
    List<User> listUsers();
    User getUserById(Long id);
    User updateUser(Long id, User user, Long[] roleIds);
    void delete(Long id);
}
