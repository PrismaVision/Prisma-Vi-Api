package com.api.prisma_vi.auth.login

import jakarta.validation.constraints.NotBlank

@JvmRecord
 data class AutheticationForm(

    @field:NotBlank(message = "Invalid cannot be blank")
    val email: String = "",
    @field:NotBlank(message = "Password cannot be blank")
    val password: String = ""

)
