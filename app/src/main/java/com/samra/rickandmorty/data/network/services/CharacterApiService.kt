package com.samra.rickandmorty.data.network.services

import android.provider.SyncStateContract
import com.samra.rickandmorty.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CharacterApiService {
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            // Add any configuration to the OkHttpClient if needed
            .build()
    }

    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .client(okHttpClient) // Set OkHttp client here
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val characterApi: CharacterApi by lazy {
        retrofitInstance.create(CharacterApi::class.java)
    }


}