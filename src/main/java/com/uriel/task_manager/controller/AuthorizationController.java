package com.uriel.task_manager.controller;

import com.uriel.task_manager.dto.LoginRequest;
import com.uriel.task_manager.entity.User;
import com.uriel.task_manager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
@RequestMapping("/api/authorization")
public class AuthorizationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthorizationController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);

        User user = userService.findByUsername(loginRequest.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("user", Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "name", user.getName(),
                "role", user.getRole().name()));

        return ResponseEntity.ok(response);
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginRequest.getUsername(),
//                            loginRequest.getPassword()));
//
//            SecurityContext securityContext = SecurityContextHolder.getContext();
//            securityContext.setAuthentication(authentication);
//
//            HttpSession session = request.getSession(true);
//            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
//
//            User user = userService.findByUsername(loginRequest.getUsername());
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", true);
//            response.put("user", Map.of(
//                    "id", user.getId(),
//                    "username", user.getUsername(),
//                    "name", user.getName(),
//                    "role", user.getRole().name()));
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", false);
//            response.put("message", "Invalid username or password");
//            return ResponseEntity.status(401).body(response);
//        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(Map.of("success", true, "message", "Logged out successfully"));
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                Objects.equals(authentication.getPrincipal(), "anonymousUser")) {
            return ResponseEntity.status(401).body(Map.of("authenticated", false));
        }

        String username = authentication.getName();
        User user = userService.findByUsername(username);

        return ResponseEntity.ok(Map.of(
                "authenticated", true,
                "user", Map.of(
                        "id", user.getId(),
                        "username", user.getUsername(),
                        "name", user.getName(),
                        "role", user.getRole().name())));
    }
}
