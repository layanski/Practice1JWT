package com.example.practice1.controller;


import com.example.practice1.entity.User;
import com.example.practice1.repository.UserRepository;
import com.example.practice1.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
//new added
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        //return userRepository.save(user);
        return userService.register(user);
    }


    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.verify(user);
//        var u = userRepository.findByUserName(user.getUserName());
//        if(!Objects.isNull(u))
//            return "success";
//        return "failure";
    }

//    new added
    @GetMapping ("/profile")
    @PreAuthorize("hasRole('USER')")
    public String userProfile() {
        return "User Profile";
    }

    @GetMapping()
    public List<User> getAllUser(){
        return userService.getAllUser();
    }
}
