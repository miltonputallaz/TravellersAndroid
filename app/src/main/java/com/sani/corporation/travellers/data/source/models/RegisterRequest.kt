package com.sani.corporation.travellers.data.source.models

import com.sani.corporation.travellers.data.source.network.models.NetworkRegisterRequest

data class RegisterRequest (
    val email: String,
    val password: String,
    val fullName: String
) {
    fun toNetwork() = NetworkRegisterRequest(
        email = email,
        password = password,
        fullName = fullName
    )
}