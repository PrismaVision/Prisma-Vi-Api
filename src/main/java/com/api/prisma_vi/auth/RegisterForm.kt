package com.api.prisma_vi.auth

import com.api.prisma_vi.user.UserRole

data class RegisterForm(

    val nickName: String = "",
    val email: String = "",
    val password: String = "",
    val role: UserRole? = null

)
