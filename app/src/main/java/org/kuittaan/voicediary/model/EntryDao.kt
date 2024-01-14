package org.kuittaan.voicediary.model

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val content: String
)

@Database(
    entities = [Entry::class],
    version = 1
)
abstract class EntryDatabase: RoomDatabase() {

    abstract val dao: EntryDao

    companion object {
        @Volatile
        private var Instance: EntryDatabase? = null

        fun getDatabase(context: Context): EntryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    EntryDatabase::class.java,
                    "entry_database"
                )
                    .build()
                    .also { Instance = it }
            }
        }

    }
}

@Dao
interface EntryDao {
    @Insert
    suspend fun insertEntry(entry: Entry): Long

    @Delete
    suspend fun deleteEntry(entry: Entry)

    @Query("DELETE FROM entries")
    suspend fun deleteAllEntries()

    @Query("SELECT * FROM entries WHERE id = :entryId")
    suspend fun getEntryById(entryId: Int): Entry

    @Query("SELECT * FROM entries ORDER BY title ASC")
    suspend fun getEntriesOrderedByDate(): List<Entry>

    @Query("SELECT COUNT(*) FROM entries")
    suspend fun getEntriesCount(): Int
}