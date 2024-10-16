package com.api.prisma_vi.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository : JpaRepository<Users, Long> {

    fun findByNickName(nickName: String): UserDetails
}