package com.sani.corporation.travellers.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.sani.corporation.travellers.JWT_KEY
import com.sani.corporation.travellers.data.source.models.LoginRequest
import com.sani.corporation.travellers.data.source.models.RegisterRequest
import com.sani.corporation.travellers.data.source.network.authentication.AuthRemoteRepository
import com.sani.corporation.travellers.data.source.network.models.NetworkLoginResponse
import com.sani.corporation.travellers.di.IoDispatcher
import com.sani.corporation.travellers.util.network.AsyncState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val remoteRepository: AuthRemoteRepository,
    @IoDispatcher private val ioCoroutineDispatcher: CoroutineDispatcher,
    private val preferences: DataStore<Preferences>
): AuthRepository {

    override suspend fun login(loginRequest: LoginRequest): Flow<AsyncState<NetworkLoginResponse>> {
        return remoteRepository.login(loginRequest.toNetwork()).map { response ->
            if (response is AsyncState.Success) {
                preferences.edit { settings ->
                    settings[JWT_KEY] = response.data.token
                }
            }
            response
        }.flowOn(ioCoroutineDispatcher)
    }

    override suspend fun register(registerRequest: RegisterRequest): Flow<AsyncState<Nothing>> {
        return remoteRepository.register(registerRequest.toNetwork()).flowOn(ioCoroutineDispatcher)
    }

}