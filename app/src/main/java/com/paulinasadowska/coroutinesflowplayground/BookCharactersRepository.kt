package com.paulinasadowska.coroutinesflowplayground

import com.paulinasadowska.coroutinesflowplayground.dao.BookCharacter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookCharactersRepository @Inject constructor() {

    fun fetchCharactersList(): Flow<List<BookCharacter>> = flow {
        delay(1000)
        emit(mockData.take(6))
        delay(1000)
        emit(mockData.take(3))
        delay(1000)
        emit(mockData)
    }

    private val mockData = listOf(
            BookCharacter("Purus", imageUrl = "https://picsum.photos/400/400"),
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