package com.sani.corporation.travellers.data

import com.sani.corporation.travellers.data.source.models.LoginRequest
import com.sani.corporation.travellers.data.source.models.RegisterRequest
import com.sani.corporation.travellers.data.source.network.models.NetworkLoginResponse
import com.sani.corporation.travellers.util.network.AsyncState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest): Flow<AsyncState<NetworkLoginResponse>>

    suspend fun register(registerRequest: RegisterRequest): Flow<AsyncState<Nothing>>
}