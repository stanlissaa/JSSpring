package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import java.util.List;

public interface RoleDao {
    void add(Role role);
    Role findByName(String name);
    List<Role> getAllRoles();
    Role findById(Long id);
}
