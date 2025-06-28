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
import com.example.seacatering.domain.usecase.CancelSubscriptionUseCase
import com.example.seacatering.domain.usecase.CreateSubscriptionUseCase
import com.example.seacatering.domain.usecase.CreateTestimonyUseCase
import com.example.seacatering.domain.usecase.CreateUserUseCase
import com.example.seacatering.domain.usecase.GetActiveSubscriptionsUseCase
import com.example.seacatering.domain.usecase.GetAllMenuUseCase
import com.example.seacatering.domain.usecase.GetAllSubscriptionsUseCase
import com.example.seacatering.domain.usecase.GetMenuDetailUseCase
import com.example.seacatering.domain.usecase.GetNewSubscriptionDataUseCase
import com.example.seacatering.domain.usecase.GetReactivationUseCase
import com.example.seacatering.domain.usecase.GetSubscriptionUseCase
import com.example.seacatering.domain.usecase.GetTestimonialsUseCase
import com.example.seacatering.domain.usecase.GetUserDataUseCase
import com.example.seacatering.domain.usecase.TogglePauseSubscriptionUseCase
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

    @Provides
    fun provideGetSubscriptionUseCase(repo: SubscriptionRepository): GetSubscriptionUseCase =
        GetSubscriptionUseCase(repo)

    @Provides
    fun provideTogglePauseSubscriptionUseCase(repo: SubscriptionRepository): TogglePauseSubscriptionUseCase =
        TogglePauseSubscriptionUseCase(repo)
    @Provides
    fun provideCancelSubscriptionUseCase(repo: SubscriptionRepository): CancelSubscriptionUseCase =
        CancelSubscriptionUseCase(repo)

    @Provides
    fun provideGetAllSubscriptionUseCase(repo: SubscriptionRepository): GetAllSubscriptionsUseCase =
        GetAllSubscriptionsUseCase(repo)

    @Provides
    fun provideGetNewSubscriptionDataUseCase(repo: SubscriptionRepository): GetNewSubscriptionDataUseCase =
        GetNewSubscriptionDataUseCase(repo)

    @Provides
    fun provideReactivationUseCase(repo: SubscriptionRepository): GetReactivationUseCase =
        GetReactivationUseCase(repo)

    @Provides
    fun provideActiveSubscriptionsUseCase(repo: SubscriptionRepository): GetActiveSubscriptionsUseCase =
        GetActiveSubscriptionsUseCase(repo)

    @Provides
    fun provideCreateTestimonyUseCase(repo: UserRepository): CreateTestimonyUseCase =
        CreateTestimonyUseCase(repo)
    @Provides
    fun provideGetUserDataUseCase(repo: UserRepository): GetUserDataUseCase =
        GetUserDataUseCase(repo)
    @Provides
    fun provideGetTestimonialsUseCase(repo: UserRepository): GetTestimonialsUseCase =
        GetTestimonialsUseCase(repo)
}