package com.sani.corporation.travellers.data.source.network.models

data class NetworkRegisterRequest(
    val email: String,
    val fullName: String,
    val password: String
)