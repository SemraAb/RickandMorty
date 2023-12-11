package com.samra.rickandmorty.data.network.services

import android.provider.SyncStateContract
import com.samra.rickandmorty.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CharacterApiService {
        val retrofitInstance  by lazy {
            Retrofit.Builder()
                .baseUrl(Constants.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(CharacterApi::class.java)
        }

}