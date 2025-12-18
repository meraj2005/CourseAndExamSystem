package ir.maktabsharif.test_app.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
