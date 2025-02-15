package com.sani.corporation.travellers.data.source.network.models

import com.google.gson.annotations.SerializedName

data class NetworkTravelResponse (
    @SerializedName("data")
    val data: List<NetworkTravel>,
    @SerializedName("count")
    val count: Int
)