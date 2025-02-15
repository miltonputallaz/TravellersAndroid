package com.sani.corporation.travellers.di

import com.sani.corporation.travellers.data.AuthRepository
import com.sani.corporation.travellers.data.source.network.FakeAuthRemoteRepository
import com.sani.corporation.travellers.data.source.network.authentication.AuthRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AuthRepositoryModule::class]
)
object AuthRepositoryTestModule {
    @Singleton
    @Provides
    fun provideAuthRepository(): AuthRepository {
        return mockk()
    }

    @Singleton
    @Provides
    fun provideAuthRemoteRepository(): AuthRemoteRepository {
        return FakeAuthRemoteRepository()
    }
}