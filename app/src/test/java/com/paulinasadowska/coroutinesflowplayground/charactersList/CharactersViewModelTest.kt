package com.paulinasadowska.coroutinesflowplayground.charactersList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.paulinasadowska.coroutinesflowplayground.dao.BookCharacter
import com.paulinasadowska.coroutinesflowplayground.dao.BookCharactersRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersViewModelTest {

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository = mockk<BookCharactersRepository>()

    @Test
    fun `should characters return characters from database`() {
        // given
        val subject = CharactersViewModel(repository)
        val characters = listOf(BookCharacter(name = "test", imageUrl = "img"))
        coEvery { repository.fetchCharactersList(any(), any()) } returns flowOf(characters)
        subject.characters.observeForever { }

        // when
        val result = subject.characters.value

        // then
        assertThat(result).isNotNull()
        assertThat(result).hasSize(1)
        assertThat(result!!.first()).isEqualTo(CharacterToDisplay(name = "test", imageUrl = "img"))
    }

    @Test
    fun `should filter change trigger new data fetch from DAO`() {
        // given
        val subject = CharactersViewModel(repository)
        val characters = listOf(BookCharacter(name = "test", imageUrl = "img"))
        coEvery { repository.fetchCharactersList(any(), any()) } returns flowOf(characters)
        subject.characters.observeForever { }

        // then
        coVerify {
            repository.fetchCharactersList(CharactersFilter.ALL, "")
        }

        // when
        subject.setStaffChecked()

        // then
        coVerify {
            repository.fetchCharactersList(CharactersFilter.STAFF, "")
        }

        // when
        subject.setStudentsChecked()

        // then
        coVerify {
            repository.fetchCharactersList(CharactersFilter.STUDENT, "")
        }
    }

    @Test
    fun `should update data on init`() {
        // when
        CharactersViewModel(repository)

        // then
        coVerify {
            repository.updateRecentCharacters()
        }
    }

    @Test
    fun `should display error when data cannot be loaded`() {
        // given
        val exception = mockk<Throwable> {
            every { message } returns "test error"
        }
        coEvery { repository.updateRecentCharacters() } throws exception
        val subject = CharactersViewModel(repository)
        subject.snackbar.observeForever {}

        // then
        coVerify {
            repository.updateRecentCharacters()
        }
        assertThat(subject.snackbar.value).isEqualTo("test error")
    }
}