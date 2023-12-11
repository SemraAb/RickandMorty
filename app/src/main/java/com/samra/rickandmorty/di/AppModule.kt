package com.samra.rickandmorty.di

import com.samra.rickandmorty.data.network.services.CharacterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideRetrofit() =
        Retrofit.Builder().baseUrl(com.samra.rickandmorty.Constants.baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit) = retrofit.create(CharacterApi::class.java)

}