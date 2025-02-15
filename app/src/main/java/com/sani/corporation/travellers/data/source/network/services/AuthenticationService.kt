package com.sani.corporation.travellers.data.source.network.services

import com.sani.corporation.travellers.data.source.network.models.NetworkLoginRequest
import com.sani.corporation.travellers.data.source.network.models.NetworkLoginResponse
import com.sani.corporation.travellers.data.source.network.models.NetworkRegisterRequest
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AuthenticationService {

    @POST("login/access-token")
    suspend fun login(@Body loginRequest: RequestBody): NetworkLoginResponse

    @POST("users/signup")
    suspend fun register(@Body loginRequest: NetworkRegisterRequest)
}