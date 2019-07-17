package com.fpt.shopping.controllers;

import com.fpt.shopping.entities.User;
import com.fpt.shopping.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Calendar;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get current user
    @GetMapping("/me")
    public ResponseEntity<User> getMe(Principal principal) {
        if (principal != null){
            String name = principal.getName();
            User user =  userService.getUserByUserName(name);

            if (user == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.OK).body(user);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/new")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        User result = userService.createUser(user);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/user/admin")
    public ResponseEntity<Boolean> createAdminAccount(){
        if(userService.getUserByUserName("admin") == null){
           User result = userService.createUser(new User("admin", "admin", "admin@gmail.com", "ROLE_ADMIN", true, new Timestamp(Calendar.getInstance().getTimeInMillis()),new Timestamp(Calendar.getInstance().getTimeInMillis())));
            if(result != null){
                return ResponseEntity.ok(true);
            }
        }
        return ResponseEntity.ok(false);
    }

    @GetMapping("/admin/user/getAll")
    public ResponseEntity<Page<User>> findAllCategoryAdmin(@RequestParam(value = "search", required = false) String search, @RequestParam(value = "page", required = false) int page, @RequestParam(value = "size", required = false) int size) {
        Page<User> result = userService.findAllUser(search, page, size);
        return ResponseEntity.ok(result);
    }
}
