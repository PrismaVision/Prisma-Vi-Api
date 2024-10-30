package com.api.prisma_vi.auth.register

import com.api.prisma_vi.user.UserRole
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class RegisterForm(

    @field:NotBlank(message = "Nickname cannot be blank")
    val nickName: String,

    @field:Email(message = "Invalid email") @field:NotBlank(message = "Invalid cannot be blank")
    val email: String,

    @field:NotBlank(message = "Password cannot be blank")
    val password: String,

    val role: UserRole? = null

)
