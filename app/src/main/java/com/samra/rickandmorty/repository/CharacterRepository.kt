package com.samra.rickandmorty.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.samra.rickandmorty.data.network.model.Characters
import com.samra.rickandmorty.data.network.model.Result
import com.samra.rickandmorty.data.network.services.CharacterApi
import com.samra.rickandmorty.paging.PagingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class CharacterRepository @Inject constructor(private val characterApi: CharacterApi ) {
    fun getFilteredCharacter(name :String = "" , gender:String ="" ,status :String="" , species: String="" ):Flow<PagingData<Result>>{
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 50
            ),
            pagingSourceFactory = { PagingDataSource(characterApi , name , gender, status, species) }
        ).flow
    }

}