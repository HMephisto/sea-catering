package com.example.seacatering.di

import com.example.seacatering.data.auth.FirebaseAuthService
import com.example.seacatering.data.firestore.FirestoreService
import com.example.seacatering.data.repository.AuthRepositoryImpl
import com.example.seacatering.data.repository.MenuRepositoryImpl
import com.example.seacatering.data.repository.SubscriptionRepositoryImpl
import com.example.seacatering.data.repository.UserRepositortImpl
import com.example.seacatering.domain.repository.AuthRepository
import com.example.seacatering.domain.repository.MenuRepository
import com.example.seacatering.domain.repository.SubscriptionRepository
import com.example.seacatering.domain.repository.UserRepository
import com.example.seacatering.domain.usecase.CreateSubscriptionUseCase
import com.example.seacatering.domain.usecase.CreateUserUseCase
import com.example.seacatering.domain.usecase.GetAllMenuUseCase
import com.example.seacatering.domain.usecase.GetMenuDetailUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore  = FirebaseFirestore.getInstance()

    @Provides
    fun provideFirestoreService(firebaseFirestore: FirebaseFirestore): FirestoreService =
        FirestoreService(firebaseFirestore)

    @Provides
    fun provideUserRepository(firestoreService: FirestoreService): UserRepository =
        UserRepositortImpl(firestoreService)

    @Provides
    fun provideMenuRepository(firestoreService: FirestoreService): MenuRepository =
        MenuRepositoryImpl(firestoreService)

    @Provides
    fun provideSubscriptionRepository(firestoreService: FirestoreService): SubscriptionRepository =
        SubscriptionRepositoryImpl(firestoreService)

    @Provides
    fun provideCreateUserUseCase(repo: UserRepository): CreateUserUseCase =
        CreateUserUseCase(repo)

    @Provides
    fun provideGetAllMenuUseCase(repo: MenuRepository): GetAllMenuUseCase =
        GetAllMenuUseCase(repo)

    @Provides
    fun provideGetMenuDetailUseCase(repo: MenuRepository): GetMenuDetailUseCase =
        GetMenuDetailUseCase(repo)

    @Provides
    fun provideCreateSubscriptionUseCase(repo: SubscriptionRepository): CreateSubscriptionUseCase =
        CreateSubscriptionUseCase(repo)
}