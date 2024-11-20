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

    fun registerUser(data: RegisterForm): ResponseEntity<Any?>{
        return when{

            data.nickName.contains(" ") || data.password.contains(" ") ->
                ResponseEntity.status(HttpStatus.CONFLICT).body(RegisterView("Input field can not have spaces"))

            (repository.existsByEmail(data.email)) ->
                ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(RegisterView("There is already an account linked to this email"))

         else ->{
            ResponseEntity.ok().body(saveUser(data))
            }
        }
    }
    private fun saveUser(data: RegisterForm): RegisterView{

        val role: UserRole = if (data.email == "prisma@prismatic"){
            UserRole.ADMIN
        } else {
            UserRole.USER
        }

        val encryptedPassword: String = BCryptPasswordEncoder().encode(data.password)
        repository.save(
            Users(
                data.nickName,
                data.email,
                encryptedPassword,
                role
            )
        )
        return RegisterView("${data.nickName} was registered successfully")
    }
}