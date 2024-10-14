package com.prisma_vision.user_api.user

import jakarta.persistence.*

@Entity(name = "USERS")
data class Users(

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int,

    @Column(unique = true)
    val login: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val role: String


)
