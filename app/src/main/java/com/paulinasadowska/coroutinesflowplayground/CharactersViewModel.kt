package com.paulinasadowska.coroutinesflowplayground

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.paulinasadowska.coroutinesflowplayground.dao.BookCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(repository: BookCharactersRepository) : ViewModel() {

    private val selectionFilters = MutableStateFlow(CharactersFilter.ALL)

    val characters: LiveData<List<CharacterToDisplay>> = repository.fetchCharactersList()
            .combine(selectionFilters) { characters, filters ->
                characters.filter {
                    when (filters) {
                        CharactersFilter.STAFF -> it.hogwartsStaff
                        CharactersFilter.STUDENT -> it.hogwartsStudent
                        else -> true
                    }
                }
            }.map { characters: List<BookCharacter> ->
                characters.map {
                    CharacterToDisplay(it.name, it.imageUrl ?: "")
                }
            }
            .asLiveData()

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