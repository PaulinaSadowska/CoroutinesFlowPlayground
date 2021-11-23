package com.paulinasadowska.coroutinesflowplayground.dao

import android.content.Context
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow

fun createDatabase(applicationContext: Context) = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "hp-characters"
).build()

@Database(entities = [BookCharacter::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookCharactersDao(): BookCharactersDao
}

@Dao
interface BookCharactersDao {
    @Query("SELECT * FROM bookcharacter")
    fun getAllCharacters(): Flow<List<BookCharacter>>
}

@Entity
data class BookCharacter(
        @PrimaryKey val name: String,
        val house: String? = null,
        val wizard: Boolean? = null,
        val hogwartsStudent: Boolean = false,
        val hogwartsStaff: Boolean = false,
        @SerializedName("image") val imageUrl: String?
)
