package com.sani.corporation.travellers.data.source.models

import com.sani.corporation.travellers.data.source.network.models.NetworkLoginRequest

data class LoginRequest (
    val username: String,
    val password: String
) {

    fun toNetwork() = NetworkLoginRequest(
        username = username,
        password = password
    )
}