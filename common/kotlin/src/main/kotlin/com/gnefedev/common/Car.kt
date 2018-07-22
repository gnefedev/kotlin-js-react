package com.gnefedev.common

import kotlinx.serialization.Serializable

@Serializable
data class Car(
    val brand: String,
    val color: String,
    val year: Int
)
