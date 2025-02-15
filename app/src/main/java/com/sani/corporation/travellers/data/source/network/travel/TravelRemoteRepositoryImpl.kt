package com.sani.corporation.travellers.data.source.network.travel

import com.sani.corporation.travellers.data.source.models.Travel
import com.sani.corporation.travellers.data.source.network.models.NetworkTravel
import com.sani.corporation.travellers.data.source.network.models.NetworkTravelResponse
import com.sani.corporation.travellers.data.source.network.services.TravelService
import com.sani.corporation.travellers.util.network.AsyncState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TravelRemoteRepositoryImpl @Inject constructor(
    val travelService: TravelService
): TravelRemoteRepository {
    override suspend fun getTravels(
        skip: Int?,
        limit: Int?
    ): Flow<AsyncState<List<NetworkTravel>>> {
        TODO("Not yet implemented")
    }

    override suspend fun addTravel(travel: NetworkTravel): Flow<AsyncState<Nothing>> {
        return flow {
            try {
                travelService.addTravel(travel)
                emit(AsyncState.SuccessEmpty)
            } catch (exception: Exception) {
                emit(AsyncState.Error(exception.message.toString()))
            }
        }
    }

}