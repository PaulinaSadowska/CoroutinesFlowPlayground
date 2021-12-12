package com.paulinasadowska.coroutinesflowplayground.di

import android.content.Context
import com.paulinasadowska.coroutinesflowplayground.dao.BookCharactersDao
import com.paulinasadowska.coroutinesflowplayground.dao.createDatabase
import com.paulinasadowska.coroutinesflowplayground.network.BookCharactersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object BookCharactersModule {

    @Provides
    fun provideBookCharactersService(): BookCharactersService {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://hp-api.herokuapp.com/api/")
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(BookCharactersService::class.java)
    }

    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    fun provideBookCharactersDatabase(
            @ApplicationContext context: Context
    ): BookCharactersDao {
        return createDatabase(applicationContext = context).bookCharactersDao()
    }
}
