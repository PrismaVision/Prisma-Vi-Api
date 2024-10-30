package com.api.prisma_vi.auth.register

import com.api.prisma_vi.user.UserRole
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@JvmRecord
data class RegisterForm(

    @field:NotBlank(message = "Nickname cannot be blank")
    val nickName: String,

    @field:Email(message = "Invalid email") @field:NotBlank(message = "Invalid cannot be blank")
    val email: String,

    @field:NotBlank(message = "Password cannot be blank")  @field:Size(min = 6, max = 16, message = "Password must contain 6 to 16 characters")
    val password: String,

    val role: UserRole? = null

)
