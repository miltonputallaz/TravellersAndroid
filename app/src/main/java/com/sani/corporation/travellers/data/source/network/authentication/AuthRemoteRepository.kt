package com.sani.corporation.travellers.data.source.network.authentication

import com.sani.corporation.travellers.data.source.network.models.NetworkLoginRequest
import com.sani.corporation.travellers.data.source.network.models.NetworkLoginResponse
import com.sani.corporation.travellers.data.source.network.models.NetworkRegisterRequest
import com.sani.corporation.travellers.util.network.AsyncState
import kotlinx.coroutines.flow.Flow

interface AuthRemoteRepository {

    suspend fun login(loginRequest: NetworkLoginRequest): Flow<AsyncState<NetworkLoginResponse>>

    suspend fun register(registerRequest: NetworkRegisterRequest): Flow<AsyncState<Nothing>>

}