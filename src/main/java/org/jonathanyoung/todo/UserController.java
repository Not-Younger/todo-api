package org.jonathanyoung.todo;

import lombok.extern.slf4j.Slf4j;
import org.jonathanyoung.todo.model.UserInfo;
import org.jonathanyoung.todo.model.UserResponse;
import org.jonathanyoung.todo.repository.UserInfoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@Slf4j
public class UserController {

    UserInfoRepository userInfoRepository;
    PasswordEncoder passwordEncoder;

    public UserController(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUser() {
        return ResponseEntity
                .status(200)
                .body(userInfoRepository
                        .findAll()
                        .stream()
                        .map(userInfo -> new UserResponse(userInfo.getId(), userInfo.getEmail(), userInfo.getRoles(), "some generated token"))
                        .toList()
                );
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserInfo userInfo) {
        userInfo.setPassword(
                passwordEncoder.encode(userInfo.getPassword())
        );
        UserInfo newUserInfo = userInfoRepository.save(userInfo);
        return ResponseEntity
                .status(200)
                .body(new UserResponse(newUserInfo.getId(), newUserInfo.getEmail(), userInfo.getRoles(), "some generated token"));
    }

    @GetMapping("/secure/hello")
    public ResponseEntity<String> secureHello() {
        return ResponseEntity
                .status(200)
                .body("Secure Hello, World!");
    }
}
