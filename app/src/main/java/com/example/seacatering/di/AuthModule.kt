package com.example.seacatering.di

import com.example.seacatering.data.auth.FirebaseAuthService
import com.example.seacatering.data.repository.AuthRepositoryImpl
import com.example.seacatering.domain.repository.AuthRepository
import com.example.seacatering.domain.usecase.LoginUseCase
import com.example.seacatering.domain.usecase.RegisterUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthService(firebaseAuth: FirebaseAuth): FirebaseAuthService =
        FirebaseAuthService(firebaseAuth)

    @Provides
    fun provideAuthRepository(authService: FirebaseAuthService): AuthRepository =
        AuthRepositoryImpl(authService)

    @Provides
    fun provideLoginUseCase(repo: AuthRepository): LoginUseCase =
        LoginUseCase(repo)

    @Provides
    fun provideRegisterUseCase(repo: AuthRepository): RegisterUseCase =
        RegisterUseCase(repo)
}