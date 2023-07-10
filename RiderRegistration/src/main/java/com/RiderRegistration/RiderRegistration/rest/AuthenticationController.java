package com.RiderRegistration.RiderRegistration.rest;

import com.RiderRegistration.RiderRegistration.entity.AuthenticationRequest;
import com.RiderRegistration.RiderRegistration.entity.AuthenticationResponse;
import com.RiderRegistration.RiderRegistration.entity.User;
import com.RiderRegistration.RiderRegistration.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody User user) {
        return ResponseEntity.ok(service.register(user));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}