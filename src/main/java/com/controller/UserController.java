package com.controller;

import com.model.User;
import com.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userSvc;
    public UserController(UserService userSvc) {
        this.userSvc = userSvc;
    }

    @GetMapping
    public List<User> all() { return userSvc.findAll(); }
    @GetMapping("/{id}") public User one(@PathVariable Long id) { return userSvc.findById(id); }
    @PostMapping
    public User create(@RequestBody User u) { return userSvc.create(u); }
    @PutMapping("/{id}") public User update(@PathVariable Long id, @RequestBody User u) {
        return userSvc.update(id, u);
    }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) {
        userSvc.delete(id);
    }
}
