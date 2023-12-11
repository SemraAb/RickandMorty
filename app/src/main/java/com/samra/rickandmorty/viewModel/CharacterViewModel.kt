package com.samra.rickandmorty.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.samra.rickandmorty.data.network.model.Characters
import com.samra.rickandmorty.data.network.model.Result
import com.samra.rickandmorty.data.network.services.CharacterApi
import com.samra.rickandmorty.paging.PagingDataSource
import com.samra.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val characterRepository: CharacterRepository) : ViewModel() {

    //var characters = MutableLiveData<Characters>()

    //    fun getAll(page:Int ){
//        CoroutineScope(Dispatchers.IO).launch{
//            CharacterRepository(characterApi).getAllCharacters(page).collect(){
//                characters.postValue(it)
//            }
//        }
//    }
    fun getCharacters(): Flow<PagingData<Result>> {
        return characterRepository.getCharacters()
    }
    fun getFilteredCharacter(name : String , status: String , gender: String): Flow<PagingData<Result>>{
        return characterRepository.getFilteredCharacter(name, status , gender)
    }
}