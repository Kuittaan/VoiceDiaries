package org.kuittaan.voicediary.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.kuittaan.voicediary.model.EntryDatabase
import org.kuittaan.voicediary.view.EntryCreateView
import org.kuittaan.voicediary.view.EntryHistoryView
import org.kuittaan.voicediary.view.NavigationItem

class Navigator {

    class NavigationItemsViewModel: ViewModel() {
        val navItems = mutableStateListOf(
            NavigationItem("1", "Create Entry"),
            NavigationItem("2", "Entry History"),
            NavigationItem("3", "Calendar"),
            NavigationItem("4", "Data export")
        )
    }

    @Composable
    fun NavigationItemDetail(feature: NavigationItem) {

        // Navigate to the selected feature when chosen

        val database = EntryDatabase.getDatabase(LocalContext.current).dao
        val entryWriting = EntryCreateView()
        val entryHistory = EntryHistoryView()
        val entryRepository = EntryRepository(database)

        when(feature.id) {
            "1" -> {
                entryWriting.writeEntryArea(entryRepository)
            }
            "2" -> {
                entryHistory.createMainView(entryRepository)
            }
            "3" -> {
                // todo: add support for calendar
            }
            "4" -> {
                // todo: add support for data export
            }
            else -> {
                Log.e("Not found", "Feature does not exist")
            }
        }
    }
}