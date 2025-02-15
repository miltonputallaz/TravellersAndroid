package com.sani.corporation.travellers.data.source.network.models

import com.google.gson.annotations.SerializedName

data class NetworkLoginRequest (
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)