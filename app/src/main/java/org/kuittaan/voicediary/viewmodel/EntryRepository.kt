package org.kuittaan.voicediary.viewmodel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kuittaan.voicediary.model.Entry
import org.kuittaan.voicediary.model.EntryDao

class EntryRepository(
    private val entryDao: EntryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun insertEntry(entry: Entry): Boolean {
        val id = withContext(ioDispatcher) {
            entryDao.insertEntry(entry)
        }

        // return false if cannot insert entry
        return if (id == -1L) {
            false
        } else {
            true
        }
    }

    suspend fun getEntriesByDate(): List<Entry> {
        return withContext(ioDispatcher) {
            entryDao.getEntriesOrderedByDate()
        }
    }

    suspend fun getEntriesCount(): Int {
        return withContext(ioDispatcher) {
            entryDao.getEntriesCount()
        }
    }

    suspend fun getEntryById(entryId: String): Entry {
        return withContext(ioDispatcher) {
            entryDao.getEntryById(entryId = entryId.toInt())
        }
    }
    // Other database operations

}