package com.paulinasadowska.coroutinesflowplayground.di

import com.paulinasadowska.coroutinesflowplayground.network.BookCharactersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object BookCharactersModule {

    @Provides
    fun provideBookCharacters(): BookCharactersService {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://hp-api.herokuapp.com/api/")
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(BookCharactersService::class.java)
    }
}
