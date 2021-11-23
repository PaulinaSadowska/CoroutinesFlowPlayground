package com.paulinasadowska.coroutinesflowplayground

import com.paulinasadowska.coroutinesflowplayground.dao.BookCharacter
import com.paulinasadowska.coroutinesflowplayground.network.BookCharactersService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookCharactersRepository @Inject constructor(
        private val charactersService: BookCharactersService
) {

    fun fetchCharactersList(): Flow<List<BookCharacter>> = flow {
        emit(charactersService.fetchAllCharacters())
    }

    private val mockData = listOf(
            BookCharacter("Purus", imageUrl = "http://hp-api.herokuapp.com/images/harry.jpg"),
            BookCharacter("Neko", imageUrl = "https://picsum.photos/400/400"),
            BookCharacter("Test", imageUrl = "https://picsum.photos/400/400"),
            BookCharacter("Test 2", imageUrl = "https://picsum.photos/400/400"),
            BookCharacter("Test 3", imageUrl = "https://picsum.photos/400/400"),
            BookCharacter("Test 4", imageUrl = "https://picsum.photos/400/400"),
            BookCharacter("Test 5", imageUrl = "https://picsum.photos/400/400"),
            BookCharacter("Test 6", imageUrl = "https://picsum.photos/400/400"),
            BookCharacter("Test 7", imageUrl = "https://picsum.photos/400/400"),
            BookCharacter("Test 9", imageUrl = "https://picsum.photos/400/400"),
            BookCharacter("Amazing cat", imageUrl = "https://picsum.photos/400/400"),
            BookCharacter("Amazing cat ", imageUrl = "https://picsum.photos/400/400")
    )
}