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
    suspend fun insertEntry(entry: Entry): Long {
        return withContext(ioDispatcher) {
            entryDao.insertEntry(entry)
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

    // Other database operations
}