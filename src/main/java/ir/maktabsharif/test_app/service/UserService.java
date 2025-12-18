package ir.maktabsharif.test_app.service;

import ir.maktabsharif.test_app.dto.TokenResponse;
import ir.maktabsharif.test_app.dto.user.*;
import ir.maktabsharif.test_app.model.User;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface UserService  extends BaseService<User, Long> {
    Optional<User> findByEmail(String email);
    TokenResponse login(UserLoginRequest userLoginRequest);
    void register(UserRegisterRequest request);
    List<UserResponse> search(UserSearchFilter filter);
    UserResponse updateUser(Long userId,UpdateUserRequest request);
    void approveUser(Long userId);
}
