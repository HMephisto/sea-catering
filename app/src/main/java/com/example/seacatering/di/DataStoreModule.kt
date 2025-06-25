package com.example.seacatering.di

import android.content.Context
import com.example.seacatering.data.local.DataStoreManager
import com.example.seacatering.data.repository.UserPreferencesRepositoryImpl
import com.example.seacatering.domain.repository.MenuRepository
import com.example.seacatering.domain.repository.UserPreferencesRepository
import com.example.seacatering.domain.repository.UserRepository
import com.example.seacatering.domain.usecase.CreateUserUseCase
import com.example.seacatering.domain.usecase.GetAllMenuUseCase
import com.example.seacatering.domain.usecase.GetUserIdUseCase
import com.example.seacatering.domain.usecase.SaveUserIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context)

    @Provides
    fun provideUserPreferencesRepository(
        manager: DataStoreManager
    ): UserPreferencesRepository = UserPreferencesRepositoryImpl(manager)

    @Provides
    fun provideSaveUserIdUseCase(repo: UserPreferencesRepository): SaveUserIdUseCase =
        SaveUserIdUseCase(repo)

    @Provides
    fun provideGetUserIdUseCase(repo: UserPreferencesRepository): GetUserIdUseCase =
        GetUserIdUseCase(repo)
}