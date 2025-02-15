package com.sani.corporation.travellers.data.source.network.travel

import com.sani.corporation.travellers.data.source.models.Travel
import com.sani.corporation.travellers.data.source.network.models.NetworkTravel
import com.sani.corporation.travellers.util.network.AsyncState
import kotlinx.coroutines.flow.Flow

interface TravelRemoteRepository {
    suspend fun getTravels(skip: Int?, limit: Int?): Flow<AsyncState<List<NetworkTravel>>>
    suspend fun addTravel(travel: NetworkTravel): Flow<AsyncState<Nothing>>
}