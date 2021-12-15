package com.paulinasadowska.coroutinesflowplayground

import androidx.lifecycle.*
import com.paulinasadowska.coroutinesflowplayground.dao.BookCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CharactersViewModel @Inject constructor(repository: BookCharactersRepository) : ViewModel() {

    private val selectionFilters = MutableStateFlow(CharactersFilter.ALL)

    val filters = selectionFilters.asLiveData()

    val characters: LiveData<List<CharacterToDisplay>> = selectionFilters
            .flatMapLatest { filters -> repository.fetchCharactersList(filters) }
            .map { characters: List<BookCharacter> ->
                characters.map {
                    CharacterToDisplay(it.name, it.imageUrl ?: "")
                }
            }
            .asLiveData()

    init {
        viewModelScope.launch {
            delay(3000)
            repository.fetchRecentCharacters()
        }
    }

    fun setStaffChecked() {
        selectionFilters.value = CharactersFilter.STAFF
    }

    fun setStudentsChecked() {
        selectionFilters.value = CharactersFilter.STUDENT
    }

    fun setAllChecked() {
        selectionFilters.value = CharactersFilter.ALL
    }
}

enum class CharactersFilter {
    STUDENT, STAFF, ALL
}