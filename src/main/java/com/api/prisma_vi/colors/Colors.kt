package com.api.prisma_vi.colors

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Colors(

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,
    val name: String,
    val hexCode: String,
    val rgbCode: String,
//    val rybPercentages: String,
    val colorDescription: String,
    val twoColorsThatMatchHex: String,
    val colorTerminology: String

)
