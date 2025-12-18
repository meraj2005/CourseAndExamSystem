package ir.maktabsharif.test_app.controllers;

import ir.maktabsharif.test_app.dto.TokenResponse;
import ir.maktabsharif.test_app.dto.user.UserLoginRequest;
import ir.maktabsharif.test_app.dto.user.UserRegisterRequest;
import ir.maktabsharif.test_app.dto.user.UserResponse;
import ir.maktabsharif.test_app.exceptions.BusinessException;
import ir.maktabsharif.test_app.mapper.UserMapper;
import ir.maktabsharif.test_app.model.User;
import ir.maktabsharif.test_app.service.UserService;
import ir.maktabsharif.test_app.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {


    private final UserService userService;


    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserRegisterRequest request) {
        userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}