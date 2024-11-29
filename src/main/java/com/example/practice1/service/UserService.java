package com.example.practice1.service;

import com.example.practice1.entity.ERole;
import com.example.practice1.entity.Role;
import com.example.practice1.entity.User;
import com.example.practice1.repository.RoleRepository;
import com.example.practice1.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
//    new added
    private final RoleRepository roleRepository;
//    **
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

//    (new added roleRepository, )
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

//    new added
    public User register(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // Assign default role
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(userRole);
//        **

        return userRepository.save(user);
    }

    public String verify(User user) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
        );

        User userAuth = userRepository.findByUserName(user.getUserName());

        if (authenticate.isAuthenticated())
            return jwtService.generateToken(userAuth);
        return "failure";
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
