package ru.hits.studentintership.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.hits.studentintership.common.data.interceptors.AuthInterceptor
import ru.hits.studentintership.data.meetings.MeetingsService
import ru.hits.studentintership.data.other.OtherService
import ru.hits.studentintership.data.positions.api.PositionsService
import java.util.concurrent.TimeUnit

object Network {
  val appJson: Json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
  }

  fun getRetrofit(client: OkHttpClient, json: Json): Retrofit = Retrofit.Builder()
    .client(client)
    .baseUrl("http://147.45.69.93:8080/")
    .addConverterFactory(
      json.asConverterFactory(
        "application/json".toMediaType()
      )
    )
    .addConverterFactory(GsonConverterFactory.create())
    .addConverterFactory(ScalarsConverterFactory.create())

  .build()

  fun getHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    val httpClientBuilder = OkHttpClient.Builder().apply {
      connectTimeout(15L, TimeUnit.SECONDS)
      readTimeout(60L, TimeUnit.SECONDS)
      writeTimeout(30L, TimeUnit.SECONDS)
      val logLevel = HttpLoggingInterceptor.Level.BODY
      addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
      addInterceptor(authInterceptor)
    }

    return httpClientBuilder.build()
  }

  fun getPositionsApi(retrofit: Retrofit): PositionsService = retrofit.create(PositionsService::class.java)

  fun getOtherApi(retrofit: Retrofit): OtherService = retrofit.create(OtherService::class.java)

  fun getMeetingsApi(retrofit: Retrofit): MeetingsService = retrofit.create(MeetingsService::class.java)
}
