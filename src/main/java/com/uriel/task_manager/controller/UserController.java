package com.uriel.task_manager.controller;

import com.uriel.task_manager.entity.User;
import com.uriel.task_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, String>>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        List<Map<String, String>> response = users.stream()
                .map(user -> Map.of(
                        "id", String.valueOf(user.getId()),
                        "username", user.getUsername(),
                        "name", user.getName(),
                        "role", user.getRole().name()))
                .toList();

        return ResponseEntity.ok(response);
    }
}
