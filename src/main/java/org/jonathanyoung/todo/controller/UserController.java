package org.jonathanyoung.todo.controller;

import lombok.extern.slf4j.Slf4j;
import org.jonathanyoung.todo.model.UserInfo;
import org.jonathanyoung.todo.model.UserResponse;
import org.jonathanyoung.todo.repository.UserInfoRepository;
import org.jonathanyoung.todo.security.JwtUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Controller
@Slf4j
public class UserController {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final DaoAuthenticationProvider authenticationProvider;

    public UserController(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, DaoAuthenticationProvider authenticationProvider) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationProvider = authenticationProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserInfo userInfo) {
        if (userInfoRepository.existsByEmail(userInfo.getEmail())) {
            return ResponseEntity
                    .status(409)
                    .build();
        }

        final UserInfo newUser = new UserInfo(
                null,
                userInfo.getName(),
                userInfo.getEmail(),
                passwordEncoder.encode(userInfo.getPassword()),
                List.of()
        );
        userInfoRepository.save(newUser);

        String jwt = jwtUtil.generateToken(newUser.getEmail());

        return ResponseEntity
                .status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new UserResponse(jwt));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserInfo userInfo) {
        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userInfo.getEmail(),
                        userInfo.getPassword()
                )
        );

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity
                .status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new UserResponse(jwt));
    }

    @GetMapping("/secure/hello")
    public ResponseEntity<String> secureHello() {
        return ResponseEntity
                .status(200)
                .body("Secure Hello, World!");
    }
}
