package com.sani.corporation.travellers.data.source.network.models

import com.google.gson.annotations.SerializedName

data class NetworkLoginResponse (
    @SerializedName("access_token")
    val token: String,
    @SerializedName("token_type")
    val tokenType: String
)