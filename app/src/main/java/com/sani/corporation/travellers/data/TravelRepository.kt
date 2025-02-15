package com.sani.corporation.travellers.data

import com.sani.corporation.travellers.data.source.models.Travel
import com.sani.corporation.travellers.util.network.AsyncState
import kotlinx.coroutines.flow.Flow

interface TravelRepository {
    suspend fun getTravels(skip: Int?, limit: Int?): Flow<AsyncState<List<Travel>>>
    suspend fun addTravel(travel: Travel): Flow<AsyncState<Nothing>>
}
