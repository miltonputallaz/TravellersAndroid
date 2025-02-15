package com.sani.corporation.travellers.data

import com.sani.corporation.travellers.data.source.models.Travel
import com.sani.corporation.travellers.data.source.network.travel.TravelRemoteRepository
import com.sani.corporation.travellers.di.IoDispatcher
import com.sani.corporation.travellers.util.network.AsyncState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class TravelRepositoryImpl @Inject constructor(
    val travelRemoteRepository: TravelRemoteRepository,
    @IoDispatcher val ioCoroutineContext: CoroutineDispatcher
): TravelRepository {
    override suspend fun getTravels(skip: Int?, limit: Int?): Flow<AsyncState<List<Travel>>> {
        TODO("Not yet implemented")
    }

    override suspend fun addTravel(travel: Travel): Flow<AsyncState<Nothing>> {
        return travelRemoteRepository.addTravel(travel.toNetwork())
            .flowOn(ioCoroutineContext)
    }
}