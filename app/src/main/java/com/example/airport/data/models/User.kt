package com.example.airport.data.models

data class User(
    val id: Int,
    val roleId: Int,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val officeId: Int,
    val birthDate: String,
    val active: Boolean
)
