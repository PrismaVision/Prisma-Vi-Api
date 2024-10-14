package com.api.prisma_vi.user

import com.api.prisma_vi.colors.Colors
import com.api.prisma_vi.palette.Palette
import jakarta.persistence.*

@Entity
data class Users(

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int,

    @Column(nullable = false)
    val name: String,

    @Column(unique = true)
    val login: String,

    @Column(nullable = false)
    val password: String,

    @OneToMany
    val palettes: List<Palette>,

    @ManyToOne
    val colorHistory: List<Colors>
)
