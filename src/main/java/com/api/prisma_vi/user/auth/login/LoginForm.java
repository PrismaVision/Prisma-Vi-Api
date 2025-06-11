package com.api.prisma_vi.user.auth.login;

import jakarta.validation.constraints.NotBlank;

public record LoginForm(
        @NotBlank(message = "Invalid cannot be blank")
        String email,
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}
