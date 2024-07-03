package ru.hits.studentintership.common.hilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.hits.studentintership.common.data.interceptors.AuthInterceptor
import ru.hits.studentintership.common.data.shared.TokenManager
import ru.hits.studentintership.data.meetings.MeetingsService
import ru.hits.studentintership.data.network.Network
import ru.hits.studentintership.data.other.OtherService
import ru.hits.studentintership.data.positions.api.PositionsService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideJson() = Network.appJson

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, json: Json) = Network.getRetrofit(client, json)

    @Singleton
    @Provides
    fun provideClient(authInterceptor: AuthInterceptor): OkHttpClient = Network.getHttpClient(authInterceptor)

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor = AuthInterceptor(tokenManager)

    @Singleton
    @Provides
    fun providePositionsApi(retrofit: Retrofit): PositionsService = Network.getPositionsApi(retrofit)

    @Singleton
    @Provides
    fun provideOtherApi(retrofit: Retrofit): OtherService = Network.getOtherApi(retrofit)

    @Singleton
    @Provides
    fun provideMeetingsApi(retrofit: Retrofit): MeetingsService = Network.getMeetingsApi(retrofit)

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager = TokenManager(context)
}