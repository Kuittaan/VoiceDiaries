package org.kuittaan.voicediary.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.kuittaan.voicediary.model.Entry
import org.kuittaan.voicediary.model.EntryDatabase
import org.kuittaan.voicediary.view.EntryCreateView
import org.kuittaan.voicediary.view.EntryHistoryView
import org.kuittaan.voicediary.view.NavigationItem
import org.kuittaan.voicediary.view.SettingsView
import org.kuittaan.voicediary.view.SingleEntryView

class Navigator {

    class NavigationItemsViewModel: ViewModel() {
        val navItems = mutableStateListOf(
            NavigationItem("1", "Create Entry"),
            NavigationItem("2", "Entry History"),
            NavigationItem("3", "Calendar"),
            NavigationItem("4", "Data export"),
            NavigationItem("5", "Single Entry View"),
            NavigationItem("6", "Settings")
        )
    }

    @Composable
    fun NavigationItemDetail(feature: NavigationItem, navController: NavController) {

        // Navigate to the selected feature when chosen

        val database = EntryDatabase.getDatabase(LocalContext.current).dao
        val entryWriting = EntryCreateView()
        val entryHistory = EntryHistoryView()
        val entryRepository = EntryRepository(database)
        val singleEntryView = SingleEntryView()
        val settingsView = SettingsView()

        when(feature.id) {
            "1" -> {
                entryWriting.writeEntryArea(entryRepository)
            }
            "2" -> {
                entryHistory.createMainView(entryRepository, navController)
            }
            "3" -> {
                // todo: add support for calendar
            }
            "4" -> {
                // todo: add support for data export
            }
            "5" -> {
                singleEntryView.createSingleEntryView()
            }
            "6" -> {
                settingsView.CreateSettingsView(entryRepository)
            }
            else -> {
                Log.e("Not found", "Feature does not exist")
            }
        }
    }
}