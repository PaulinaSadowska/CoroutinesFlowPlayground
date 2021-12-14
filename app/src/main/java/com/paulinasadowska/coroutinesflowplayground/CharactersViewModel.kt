package com.paulinasadowska.coroutinesflowplayground

import androidx.lifecycle.*
import com.paulinasadowska.coroutinesflowplayground.dao.BookCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CharactersViewModel @Inject constructor(repository: BookCharactersRepository) : ViewModel() {

    private val _snackbar = MutableLiveData<String?>()

    val snackbar: LiveData<String?>
        get() = _snackbar

    private val _spinner = MutableLiveData(false)

    val spinner: LiveData<Boolean>
        get() = _spinner

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
        selectionFilters.value = CharactersFilter.ALL
        loadDataFor(selectionFilters) { // todo -  to many requests! and data not displayed initially
            _spinner.value = true
            repository.fetchRecentCharacters()
        }
    }

    private fun <T> loadDataFor(source: StateFlow<T>, block: suspend (T) -> Unit) {
        source
                .mapLatest(block)
                .onEach { _spinner.value = false }
                .catch { t -> _snackbar.value = t.message }
                .launchIn(viewModelScope)

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