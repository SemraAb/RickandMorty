package com.samra.rickandmorty.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.samra.rickandmorty.data.network.model.Result
import com.samra.rickandmorty.data.network.services.CharacterApi
import java.io.IOException

class PagingDataSource(
    private val characterApi: CharacterApi,
    val name: String = "",
    val gender: String ="",
    val status: String ="",
    val species: String =""
) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = characterApi.getAll(page = nextPageNumber , name , gender , status , species)
            val data = response.body()
            println(data)
            if (data !=null) {
                LoadResult.Page(
                    data = data.results ?: emptyList(),
                    prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                    nextKey = if (data.results.isNullOrEmpty()) null else nextPageNumber + 1
                )
            } else {
                if (response.code() == 404) LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                ) else{
                    LoadResult.Error(Exception("Empty response"))
                }
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}