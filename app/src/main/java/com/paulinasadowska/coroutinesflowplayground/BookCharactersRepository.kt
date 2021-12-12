package com.paulinasadowska.coroutinesflowplayground

import com.paulinasadowska.coroutinesflowplayground.dao.BookCharacter
import com.paulinasadowska.coroutinesflowplayground.dao.BookCharactersDao
import com.paulinasadowska.coroutinesflowplayground.network.BookCharactersService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookCharactersRepository @Inject constructor(
        private val charactersService: BookCharactersService,
        private val charactersDao: BookCharactersDao,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    fun fetchCharactersList(): Flow<List<BookCharacter>> = flow {
        emit(charactersDao.getAllCharacters())
        val characters = charactersService.fetchAllCharacters()
        withContext(defaultDispatcher) {
            charactersDao.saveCharacters(characters)
        }
        emit(characters)
    }
}