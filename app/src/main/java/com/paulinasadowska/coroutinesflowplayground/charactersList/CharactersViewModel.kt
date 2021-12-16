package com.paulinasadowska.coroutinesflowplayground.charactersList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.paulinasadowska.coroutinesflowplayground.dao.BookCharacter
import com.paulinasadowska.coroutinesflowplayground.dao.BookCharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CharactersViewModel @Inject constructor(repository: BookCharactersRepository) : ViewModel() {

    private val selectionFilters = MutableStateFlow(CharactersFilter.ALL)
    val filters = selectionFilters.asLiveData()

    private val _searchedName = MutableStateFlow("")

    val characters: LiveData<List<CharacterToDisplay>> = selectionFilters
            .combine(_searchedName) { filters, searchedName -> filters to searchedName }
            .flatMapLatest { (filters, searchedName) -> repository.fetchCharactersList(filters, searchedName) }
            .map { characters: List<BookCharacter> ->
                characters
                        .map { CharacterToDisplay(it.name, it.imageUrl ?: "") }
            }
            .asLiveData()

    init {
        viewModelScope.launch {
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

    fun setSearchedName(name: String) {
        _searchedName.value = name
    }
}

enum class CharactersFilter {
    STUDENT, STAFF, ALL
}