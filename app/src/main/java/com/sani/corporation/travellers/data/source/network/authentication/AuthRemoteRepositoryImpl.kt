package com.sani.corporation.travellers.data.source.network.authentication

import com.sani.corporation.travellers.data.source.network.models.NetworkLoginRequest
import com.sani.corporation.travellers.data.source.network.models.NetworkLoginResponse
import com.sani.corporation.travellers.data.source.network.models.NetworkRegisterRequest
import com.sani.corporation.travellers.data.source.network.services.AuthenticationService
import com.sani.corporation.travellers.util.network.AsyncState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.log


@Singleton
class AuthRemoteRepositoryImpl @Inject constructor(
    private val authenticationService: AuthenticationService
): AuthRemoteRepository {
    override suspend fun login(loginRequest: NetworkLoginRequest): Flow<AsyncState<NetworkLoginResponse>> {
        return flow {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", loginRequest.username)
                .addFormDataPart("password", loginRequest.password)
                .build()
            try {
                val result = authenticationService.login(requestBody)
                emit(AsyncState.Success(result))
            } catch (exception: HttpException) {
                emit(AsyncState.Error(exception.message ?: "Error trying to login in"))
            }
        }
    }

    override suspend fun register(registerRequest: NetworkRegisterRequest): Flow<AsyncState<Nothing>> {
        return flow {
            try {
                authenticationService.register(registerRequest)
                emit(AsyncState.SuccessEmpty)
            } catch (exception: HttpException) {
                emit(AsyncState.Error(exception.message ?: "Error"))
            }
        }
    }
}