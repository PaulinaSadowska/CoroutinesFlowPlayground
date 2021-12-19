package com.paulinasadowska.coroutinesflowplayground.charactersList

import androidx.lifecycle.*
import com.paulinasadowska.coroutinesflowplayground.dao.BookCharacter
import com.paulinasadowska.coroutinesflowplayground.dao.BookCharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CharactersViewModel @Inject constructor(repository: BookCharactersRepository) : ViewModel() {

    private val selectionFilters = MutableStateFlow(CharactersFilter.ALL)
    val filters = selectionFilters.asLiveData()

    private val _snackbar = MutableLiveData<String>()
    val snackbar: LiveData<String> = _snackbar

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean> = _spinner

    private val _searchedName = MutableStateFlow("")

    val characters: LiveData<List<CharacterToDisplay>> = selectionFilters
            .combine(_searchedName) { filters, searchedName -> filters to searchedName }
            .flatMapLatest { (filters, searchedName) -> repository.fetchCharactersList(filters, searchedName) }
            .filterNot { it.isEmpty() }
            .map { characters: List<BookCharacter> ->
                val charactersToDisplay = characters.map {
                    CharacterToDisplay(it.name, it.imageUrl ?: "")
                }
                _spinner.value = false
                charactersToDisplay
            }
            .asLiveData()

    init {
        _spinner.value = true
        viewModelScope.launch {
            try {
                repository.fetchRecentCharacters()
            } catch (e: Throwable) {
                _snackbar.value = e.message
            }
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

    fun clearSnackbarMessage() {
        _snackbar.value = ""
    }
}

enum class CharactersFilter {
    STUDENT, STAFF, ALL
}