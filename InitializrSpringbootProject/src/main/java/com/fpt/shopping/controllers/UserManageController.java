package com.fpt.shopping.controllers;

import com.fpt.shopping.entities.User;
import com.fpt.shopping.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserManageController {
    private final UserRepository userRepository;

    public UserManageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    Iterable<User> listUser() {
        return this.userRepository.findAll();
    }

    @PostMapping("{id}/disable")
    void disableUser(@PathVariable int id) {
        User disable = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));

        disable.setActive(false);
        userRepository.save(disable);
    }
}
