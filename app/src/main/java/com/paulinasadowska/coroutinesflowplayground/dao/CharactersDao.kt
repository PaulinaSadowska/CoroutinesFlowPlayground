package com.paulinasadowska.coroutinesflowplayground.dao

import android.content.Context
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow

fun createDatabase(applicationContext: Context) = Room.databaseBuilder(
        applicationContext,
        CharactersDatabase::class.java, "bookCharacters"
).build()

@Database(entities = [BookCharacter::class], version = 1)
abstract class CharactersDatabase : RoomDatabase() {
    abstract fun bookCharactersDao(): BookCharactersDao
}

@Dao
interface BookCharactersDao {
    @Query("SELECT * FROM bookCharacter")
    fun getAllCharacters(): Flow<List<BookCharacter>>

    @Query("DELETE FROM bookCharacter")
    fun deleteAll()

    @Insert
    fun saveCharacters(characters: List<BookCharacter>)
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
