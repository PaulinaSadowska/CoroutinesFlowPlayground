package com.paulinasadowska.coroutinesflowplayground.details

import androidx.lifecycle.*
import com.paulinasadowska.coroutinesflowplayground.dao.BookCharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
        repository: BookCharactersRepository,
        state: SavedStateHandle
) : ViewModel() {

    private val characterName = state.get<String>("name") ?: ""
    private val _character = MutableLiveData(CharacterDetailsToDisplay(name = characterName))

    val character: LiveData<CharacterDetailsToDisplay> = _character

    init {
        viewModelScope.launch {
            repository.fetchCharacter(characterName).apply {
                _character.value = CharacterDetailsToDisplay(
                        name = name,
                        imageFromUrl = imageUrl,
                        house = house
                )
            }
        }
    }
}

data class CharacterDetailsToDisplay(
        val name: String,
        val imageFromUrl: String? = null,
        val house: String? = null,
)