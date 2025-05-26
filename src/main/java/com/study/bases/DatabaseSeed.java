package com.study.bases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.study.entities.Role;
import com.study.entities.User;
import com.study.repos.IRoleRepository;
import com.study.repos.IUserRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseSeed implements CommandLineRunner {

    @Autowired
    private IUserRepo userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        CreateRoles();
        CreateUsers();
        System.out.println("Database seed successfully");
    }

    private void CreateRoles() {
        List<Role> all = roleRepository.findAll();

        if (!all.isEmpty()) return;

        List<Role> roles = List.of(
                new Role("ROLE_ADMIN"),
                new Role("ROLE_PARENT"),
                new Role("ROLE_CHILD")
        );

        roleRepository.saveAll(roles);
    }

    private void CreateUsers() {
        List<User> all = userRepository.findAll();
        List<Role> roles = roleRepository.findAll();

        boolean hasAdmin = all.stream()
                .flatMap(user -> user.getRoles().stream())
                .anyMatch(role -> "ROLE_ADMIN".equals(role.getName()));

        if (hasAdmin) return;

        Role admin = roles.stream()
                .filter(role -> "ROLE_ADMIN".equals(role.getName()))
                .findFirst()
                .orElse(null);

        if (admin == null) return;

        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin123"));
        user.setEmail("admin@gmail.com");
        user.setFullName("Super User");
        user.setRoles(List.of(admin));

        userRepository.save(user);
    }
}
