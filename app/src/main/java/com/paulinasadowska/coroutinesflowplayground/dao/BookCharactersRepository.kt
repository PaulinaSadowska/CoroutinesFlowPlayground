package com.paulinasadowska.coroutinesflowplayground.dao

import com.paulinasadowska.coroutinesflowplayground.charactersList.CharactersFilter
import com.paulinasadowska.coroutinesflowplayground.network.BookCharactersService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookCharactersRepository @Inject constructor(
        private val charactersService: BookCharactersService,
        private val charactersDao: BookCharactersDao,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun fetchRecentCharacters() {
        return withContext(defaultDispatcher) {
            val characters = charactersService.fetchAllCharacters()
            charactersDao.saveCharacters(characters)
        }
    }

    suspend fun fetchCharactersList(filters: CharactersFilter): Flow<List<BookCharacter>> =
            charactersDao.getAllCharacters().map {
                it.applyMainSafeFilterAndSort(filters)
            }


    suspend fun fetchCharacter(characterName: String): BookCharacter =
            charactersDao.getCharacter(characterName)

    private suspend fun List<BookCharacter>.applyMainSafeFilterAndSort(filters: CharactersFilter): List<BookCharacter> {
        return withContext(defaultDispatcher) {
            applyFilter(filters).applySort()
        }
    }

    private fun List<BookCharacter>.applySort(): List<BookCharacter> {
        return this.sortedBy { it.name }
    }

    private fun List<BookCharacter>.applyFilter(filters: CharactersFilter): List<BookCharacter> {
        return filter {
            if (it.imageUrl.isNullOrBlank()) {
                false
            } else {
                when (filters) {
                    CharactersFilter.STAFF -> it.hogwartsStaff
                    CharactersFilter.STUDENT -> it.hogwartsStudent
                    else -> true
                }
            }
        }
    }
}