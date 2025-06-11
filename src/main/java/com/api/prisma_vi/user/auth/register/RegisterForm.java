package com.api.prisma_vi.user.auth.register;

import com.api.prisma_vi.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterForm(

        @NotBlank(message = "Nickname cannot be blank")
        String nickname,

        @Email(message = "Invalid email") @NotBlank(message = "Invalid cannot be blank")
        String email,

        @NotBlank(message = "Password cannot be blank") @Size(min = 6, max = 16, message = "Password must contain 6 to 16 characters")
        String password,

        UserRole role
) {
}
