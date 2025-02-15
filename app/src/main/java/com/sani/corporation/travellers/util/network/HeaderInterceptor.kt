package com.sani.corporation.travellers.util.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.sani.corporation.travellers.JWT_KEY
import com.sani.corporation.travellers.LoginState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeaderInterceptor @Inject constructor(
    private val preferences: DataStore<Preferences>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        if (!chain.request().url.toString().contains("login")) {
            val token = runBlocking {
                getToken()
            }
            request.addHeader("Authorization", "Bearer $token")
        }



        request.addHeader("Content-Type", "application/json")
        request.addHeader("Accept", "application/json")
        return chain.proceed(request.build())
    }

    suspend fun getToken() : String? {
        val preference = preferences.data.first()
        return preference[JWT_KEY]
    } // this is inside the datastore local repository.

}



const val JWT_HEADER = ""