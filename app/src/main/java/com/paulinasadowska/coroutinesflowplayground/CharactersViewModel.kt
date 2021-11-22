package com.paulinasadowska.coroutinesflowplayground

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(repository: BookCharactersRepository) : ViewModel() {

    val characters: LiveData<List<CharacterToDisplay>> = repository.fetchCharactersList()
            .map { characters ->
                characters.map { CharacterToDisplay(it.name, it.imageUrl ?: "") }
            }
            .asLiveData()
}