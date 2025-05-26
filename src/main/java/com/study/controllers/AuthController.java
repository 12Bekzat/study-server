package com.study.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.entities.JWTTokenUtils;
import com.study.entities.JwtRequest;
import com.study.entities.JwtResponse;
import com.study.entities.Role;
import com.study.entities.UpdateUserDto;
import com.study.entities.User;
import com.study.entities.UserDto;
import com.study.repos.IRoleRepository;
import com.study.repos.IUserRepo;
import com.study.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepo userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private JWTTokenUtils jwtTokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/api/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest jwtRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (Exception e) {
            return new ResponseEntity<>("Неправильный логин или пароль", HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/api/users/create")
    public ResponseEntity<User> create(@RequestBody UserDto user) {
        User createUser = new User();
        createUser.setEmail(user.getEmail());
        createUser.setUsername(user.getUsername());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getParentId() != null) createUser.setParentId(user.getParentId());
        createUser.setUrl(user.getUrl());
        createUser.setFullName(user.getFullName());
        createUser.setExtra(user.getExtra());

        List<Role> list = user.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName))
                .filter(Optional::isPresent)
                .map(Optional::get).toList();
        createUser.setRoles(list);

        return ResponseEntity.ok(userRepository.save(createUser));
    }

    @PostMapping("/api/users/update")
    public ResponseEntity<User> update(@RequestBody UpdateUserDto updateUserDto) {
        Optional<User> byId = userRepository.findById(updateUserDto.getId());
        if (byId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = byId.get();
        UserDto userDto = updateUserDto.getUserDto();

        if(userDto.getEmail() != null && !userDto.getEmail().isEmpty()) user.setEmail(userDto.getEmail());
        if(userDto.getFullName() != null && !userDto.getFullName().isEmpty()) user.setFullName(userDto.getFullName());
        if(userDto.getExtra() != null && !userDto.getExtra().isEmpty()) user.setExtra(userDto.getExtra());
        if(userDto.getPassword() != null && !userDto.getPassword().isEmpty()) user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if(userDto.getParentId() != null && !userDto.getParentId().toString().isEmpty()) user.setParentId(userDto.getParentId());

        if (userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {
            List<Role> list = userDto.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName))
                    .filter(Optional::isPresent)
                    .map(Optional::get).toList();
            user.setRoles(list);
        }

        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/api/users/remove/{id}")
    public ResponseEntity<User> remove(@PathVariable Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
        userRepository.deleteById(id);
        return ResponseEntity.ok(byId.get());
    }

    @GetMapping("/api/users/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("Пользователь не авторизован", HttpStatus.UNAUTHORIZED);
        }

        String username = authentication.getName(); // это login/email, зависит от реализации UserDetails

        Optional<User> optionalUser = userRepository.findByUsername(username); // или findByEmail()
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(optionalUser.get());
    }

    @GetMapping("/api/users/getPaged")
    public ResponseEntity<List<User>> getPaged() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/api/users/getById/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userRepository.findById(id).get());
    }
}
