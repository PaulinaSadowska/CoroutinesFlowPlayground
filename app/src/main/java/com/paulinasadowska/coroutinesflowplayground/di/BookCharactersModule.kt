package com.paulinasadowska.coroutinesflowplayground.di

import android.content.Context
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.paulinasadowska.coroutinesflowplayground.dao.BookCharactersDao
import com.paulinasadowska.coroutinesflowplayground.dao.createDatabase
import com.paulinasadowska.coroutinesflowplayground.network.BookCharactersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object BookCharactersModule {

    @Provides
    fun provideBookCharactersService(networkFlipperPlugin: NetworkFlipperPlugin): BookCharactersService {
        val okHttp = OkHttpClient.Builder()
                .addNetworkInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://hp-api.herokuapp.com/api/")
                .client(okHttp)
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

@Module
@InstallIn(SingletonComponent::class)
class GlobalModule {
    @Provides
    @Singleton
    fun networkPlugin(): NetworkFlipperPlugin = NetworkFlipperPlugin()
}