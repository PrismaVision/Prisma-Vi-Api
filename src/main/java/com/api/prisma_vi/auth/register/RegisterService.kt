package com.api.prisma_vi.auth.register

import com.api.prisma_vi.user.UserRole
import com.api.prisma_vi.user.Users
import com.api.prisma_vi.user.UsersRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegisterService(private val repository: UsersRepository) {

    fun registerUser(data: RegisterForm): ResponseEntity<Any>{


        if (repository.existsByEmail(data.email))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("There is already an account linked to this email")

        val encryptedPassword: String = BCryptPasswordEncoder().encode(data.password);
        val newUser = Users(data.nickName, data.email, encryptedPassword, UserRole.USER)

        repository.save(newUser)
        return ResponseEntity.ok().build()
    }
}