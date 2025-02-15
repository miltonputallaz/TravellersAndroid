/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sani.corporation.travellers.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences

import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.sani.corporation.travellers.data.AuthRepository
import com.sani.corporation.travellers.data.AuthRepositoryImpl
import com.sani.corporation.travellers.data.DefaultTaskRepository
import com.sani.corporation.travellers.data.TaskRepository
import com.sani.corporation.travellers.data.TravelRepository
import com.sani.corporation.travellers.data.TravelRepositoryImpl
import com.sani.corporation.travellers.data.source.local.TravellersDatabase
import com.sani.corporation.travellers.data.source.network.NetworkDataSource
import com.sani.corporation.travellers.data.source.network.TaskNetworkDataSource
import com.sani.corporation.travellers.data.source.network.authentication.AuthRemoteRepository
import com.sani.corporation.travellers.data.source.network.authentication.AuthRemoteRepositoryImpl
import com.sani.corporation.travellers.data.source.network.services.AuthenticationService
import com.sani.corporation.travellers.data.source.network.services.TravelService
import com.sani.corporation.travellers.data.source.network.travel.TravelRemoteRepository
import com.sani.corporation.travellers.data.source.network.travel.TravelRemoteRepositoryImpl
import com.sani.corporation.travellers.util.network.HeaderInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTaskRepository(repository: DefaultTaskRepository): TaskRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAuthRemoteRepository(repository: AuthRemoteRepositoryImpl): AuthRemoteRepository

    @Singleton
    @Binds
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository
}

@Module
@InstallIn(SingletonComponent::class)
    abstract class TravelRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTravelRemoteRepository(repository: TravelRemoteRepositoryImpl): TravelRemoteRepository

    @Singleton
    @Binds
    abstract fun bindTravelRepository(repository: TravelRepositoryImpl): TravelRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindNetworkDataSource(dataSource: TaskNetworkDataSource): NetworkDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): TravellersDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TravellersDatabase::class.java,
            "Travellers.db"
        ).build()
    }

    @Provides
    fun provideTaskDao(database: TravellersDatabase): com.sani.corporation.travellers.data.source.local.TaskDao = database.taskDao()
}


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext,USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    @Singleton
    @Provides
    fun provideRetrofitBase(interceptor: HeaderInterceptor): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }



}


@InstallIn(SingletonComponent::class)
@Module
object ServicesModule {
    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit) = retrofit.create(AuthenticationService::class.java)

    @Singleton
    @Provides
    fun provideTravelService(retrofit: Retrofit) = retrofit.create(TravelService::class.java)

}

const val USER_PREFERENCES = "PREFERENCES"
