package com.example.userloginserver.controller;

import com.example.userloginserver.model.User;
import com.example.userloginserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8002", allowCredentials = "true")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login/account")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginInfo) {
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");

        System.out.println("username: " + username + ", password: " + password);

        User user = userRepository.findByUsername(username);
        Map<String, Object> responseBody = new HashMap<>();

        if (user != null && password.equals(user.getPassword())) { // 直接比较密码（在实际应用中不推荐）
            responseBody.put("status", "ok");
            responseBody.put("type", "account");
            return ResponseEntity.ok(responseBody);
        } else {
            responseBody.put("status", "error");
            responseBody.put("type", "account");
            return ResponseEntity.ok(responseBody);
        }
    }

    @PostMapping("/login/outLogin")
    public ResponseEntity<Map<String, Object>> logout() {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User newUser) {
        User user = userRepository.findByUsername(newUser.getUsername());
        if (user != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new HashMap<String, Object>() {{
                put("success", false);
                put("status", "error");
                put("message", "用户名已存在");
            }});
        }

        userRepository.save(newUser);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("status", "ok");
        return ResponseEntity.ok(response);
    }
}
