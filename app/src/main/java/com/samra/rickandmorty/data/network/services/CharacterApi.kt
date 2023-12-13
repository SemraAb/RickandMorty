package com.samra.rickandmorty.data.network.services

import com.samra.rickandmorty.data.network.model.Characters
import com.samra.rickandmorty.data.network.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {
    @GET("character")
    suspend fun getAll(
        @Query("page")page:Int,
        @Query("name")name : String = "",
        @Query("gender") gender: String = "",
        @Query("status") status: String = "",
        @Query("species") species: String = ""

    ): Response<Characters>
}