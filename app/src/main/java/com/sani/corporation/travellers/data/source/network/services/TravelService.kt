package com.sani.corporation.travellers.data.source.network.services

import com.sani.corporation.travellers.data.source.network.models.NetworkLoginResponse
import com.sani.corporation.travellers.data.source.network.models.NetworkRegisterRequest
import com.sani.corporation.travellers.data.source.network.models.NetworkTravel
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface TravelService {

    @POST("travels/")
    suspend fun addTravel(@Body networkRequest: NetworkTravel)
}