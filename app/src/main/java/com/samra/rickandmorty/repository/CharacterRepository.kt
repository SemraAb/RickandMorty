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


    fun getCharacters(): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                maxSize = 200
            ),
            pagingSourceFactory = { PagingDataSource(characterApi) }
        ).flow
    }

    fun getFilteredCharacter(name:String ,status :String , gender: String ):Flow<PagingData<Result>>{
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                maxSize = 200
            ),
            pagingSourceFactory = { PagingDataSource(characterApi) }
        ).flow
    }

//    fun  getAllCharacters(page : Int): Flow<Characters> = flow {
//        val result = characterApi.getAll(page).body()
//        if (result != null) {
//            emit(result)
//        }
//    }
}