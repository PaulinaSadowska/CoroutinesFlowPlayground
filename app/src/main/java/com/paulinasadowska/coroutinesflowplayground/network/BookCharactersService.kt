package com.paulinasadowska.coroutinesflowplayground.network

import com.paulinasadowska.coroutinesflowplayground.dao.BookCharacter
import retrofit2.http.GET


interface BookCharactersService {
    @GET("characters")
    suspend fun fetchAllCharacters(): List<BookCharacter>
}