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

    private val selectionFilters = MutableStateFlow(SelectionFilters())

    val characters: LiveData<List<CharacterToDisplay>> = repository.fetchCharactersList()
            .combine(selectionFilters) { characters, filters ->
                characters.filter {
                    if (filters.staff) {
                        it.hogwartsStaff
                    } else true
                }.filter {
                    if (filters.student) {
                        it.hogwartsStudent
                    } else true
                }
            }.map { characters: List<BookCharacter> ->
                characters.map {
                    CharacterToDisplay(it.name, it.imageUrl ?: "")
                }
            }
            .asLiveData()

    fun setStaffChecked(checked: Boolean) {
        selectionFilters.value = selectionFilters.value.copy(staff = checked)
    }

    fun setStudentsChecked(checked: Boolean) {
        selectionFilters.value = selectionFilters.value.copy(student = checked)
    }
}

data class SelectionFilters(
        val staff: Boolean = false,
        val student: Boolean = false,
)