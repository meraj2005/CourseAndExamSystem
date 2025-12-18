package ir.maktabsharif.test_app.controllers;

import ir.maktabsharif.test_app.dto.user.UpdateUserRequest;
import ir.maktabsharif.test_app.dto.user.UserResponse;
import ir.maktabsharif.test_app.dto.user.UserSearchFilter;
import ir.maktabsharif.test_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {


    private final UserService userService;


    public AdminUserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<UserResponse>> searchUsers(UserSearchFilter filter) {
        return ResponseEntity.ok(userService.search(filter));
    }


    @PutMapping("/{userId}/approve")
    public ResponseEntity<Void> approve(@PathVariable Long userId) {
        userService.approveUser(userId);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> update(@PathVariable Long userId,
                                       @Valid @RequestBody UpdateUserRequest request) {
        UserResponse userResponse = userService.updateUser(userId, request);
        return ResponseEntity.ok(userResponse);
    }
    @GetMapping("/_debug")
    public String debug() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().toString();
    }

}