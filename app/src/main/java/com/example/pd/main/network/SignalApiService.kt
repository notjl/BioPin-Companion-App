package com.example.pd.main.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://em-tech-dywahfblh8dfgg.up.railway.app"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface SignalApiService {
    @GET("/")
    suspend fun getSignal(): List<SignalData>

    @GET("/")
    fun getSignalSync(): List<SignalData>
}

object SignalApi {
    val retrofitService: SignalApiService by lazy {
        retrofit.create(SignalApiService::class.java)
    }
}