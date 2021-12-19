package com.paulinasadowska.coroutinesflowplayground.dao

import com.paulinasadowska.coroutinesflowplayground.network.BookCharactersService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class BookCharactersRepositoryTest {

    private val charactersService = mockk<BookCharactersService>()
    private val charactersDao = mockk<BookCharactersDao>(relaxed = true)
    private val dispatcher = TestCoroutineDispatcher()

    private val repository = BookCharactersRepository(charactersService, charactersDao, dispatcher)

    @Test
    fun `should fetchRecentCharacters fave fetched data to db`() = runBlockingTest {
        // given
        val characters = mockk<List<BookCharacter>>()
        coEvery { charactersService.fetchAllCharacters() } returns characters

        // when
        repository.updateRecentCharacters()

        // then
        coVerify {
            charactersDao.saveCharacters(characters)
        }
    }
}