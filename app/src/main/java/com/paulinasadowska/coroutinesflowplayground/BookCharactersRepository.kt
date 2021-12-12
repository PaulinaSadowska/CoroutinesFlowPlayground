package com.paulinasadowska.coroutinesflowplayground

import com.paulinasadowska.coroutinesflowplayground.dao.BookCharacter
import com.paulinasadowska.coroutinesflowplayground.dao.BookCharactersDao
import com.paulinasadowska.coroutinesflowplayground.network.BookCharactersService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookCharactersRepository @Inject constructor(
        private val charactersService: BookCharactersService,
        private val charactersDao: BookCharactersDao
) {

    fun fetchCharactersList(): Flow<List<BookCharacter>> = charactersDao
            .getAllCharacters()
            .flatMapLatest {
                flow {
                    val characters = charactersService.fetchAllCharacters()
                    emit(characters)
                }
            }

    private fun saveCharacters(bookCharacter: List<BookCharacter>){
        charactersDao.deleteAll()
        charactersDao.saveCharacters(bookCharacter)
    }
}