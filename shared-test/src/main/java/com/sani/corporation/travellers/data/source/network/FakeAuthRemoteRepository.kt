package com.sani.corporation.travellers.data.source.network

import com.sani.corporation.travellers.data.source.network.authentication.AuthRemoteRepository
import com.sani.corporation.travellers.data.source.network.models.NetworkLoginRequest
import com.sani.corporation.travellers.data.source.network.models.NetworkLoginResponse
import com.sani.corporation.travellers.data.source.network.models.NetworkRegisterRequest
import com.sani.corporation.travellers.util.network.AsyncState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAuthRemoteRepository: AuthRemoteRepository {
    override suspend fun login(loginRequest: NetworkLoginRequest): Flow<AsyncState<NetworkLoginResponse>> {
        return flow {  }
    }

    override suspend fun register(registerRequest: NetworkRegisterRequest): Flow<AsyncState<Nothing>> {
        return flow {  }
    }
}