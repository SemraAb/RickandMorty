package com.samra.rickandmorty.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.samra.rickandmorty.data.network.model.Result
import com.samra.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {

    private val _characterPagingData = MutableStateFlow<PagingData<Result>>(PagingData.empty())
    val characterPagingData = _characterPagingData.cachedIn(viewModelScope)

    private val _filterState = MutableStateFlow(
        FilterState(
            name = "",
            gender = GenderType.ALL,
            status = StatusType.ALL,
            species = SpeciesType.ALL
        )
    )
    val filterState = _filterState.asStateFlow()
    init {
        getCharacters()
    }
    fun updateName(value: String) {
        _filterState.update {
            it.copy(name = value)
        }
    }
    fun updateGender(value: GenderType) {
        _filterState.update {
            it.copy(gender = value)
        }
    }
    fun updateSpecies(value: SpeciesType) {
        _filterState.update {
            it.copy(species = value)
        }
    }
    fun updateStatus(value: StatusType) {
        _filterState.update {
            it.copy(status = value)
        }
    }
    fun getCharacters(
        name: String = filterState.value.name,
        gender: GenderType = filterState.value.gender,
        status: StatusType = filterState.value.status,
        species: SpeciesType = filterState.value.species
    ) = characterRepository.getFilteredCharacter(
            name = name,
            gender = gender.genderName,
            status = status.statusName,
            species = species.speciesName
        ).onEach {
        Log.e("TEST", "getCharacters: $it", )
            _characterPagingData.value = it
        }.launchIn(viewModelScope)
}
data class FilterState(
    val name: String,
    val gender: GenderType,
    val status: StatusType,
    val species: SpeciesType
)

enum class GenderType(val genderName: String) {
    ALL(""),
    MALE("male"),
    FEMALE("female"),
    GENDERLESS("genderless"),
    UNKNOWN("unknown");

    companion object {
        fun from(value: String) =
            values().find { it.genderName.lowercase() == value.lowercase() } ?: ALL
    }
}

enum class StatusType(val statusName: String) {
    ALL(""),
    ALIVE("alive"),
    DEAD("dead"),
    UNKNOWN("unknown");

    companion object {
        fun from(value: String) =
            values().find { it.statusName.lowercase() == value.lowercase() } ?: ALL
    }
}

enum class SpeciesType(val speciesName: String) {
    ALL(""),
    HUMAN("human"),
    ALIEN("alien"),
    UNKNOWN("unknown");

    companion object {
        fun from(value: String) =
            values().find { it.speciesName.lowercase() == value.lowercase() } ?: ALL
    }
}

