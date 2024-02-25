package org.kuittaan.voicediary.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import org.kuittaan.voicediary.model.EntryDatabase
import org.kuittaan.voicediary.view.EntryCreateView
import org.kuittaan.voicediary.view.EntryHistoryView
import org.kuittaan.voicediary.view.SettingsView
import org.kuittaan.voicediary.view.SingleEntryView

class Navigator {

    data class NavigationItem(val id: String, val featureName: String)

    class NavigationItemsViewModel: ViewModel() {
        val navItems = mutableStateListOf(
            NavigationItem(AppNavigation.CreateEntryRoute, "Create Entry"),
            NavigationItem(AppNavigation.EntryHistoryRoute, "Entry History"),
            NavigationItem(AppNavigation.CalendarRoute, "Calendar"),
            NavigationItem(AppNavigation.DataExportRoute, "Data export"),
            NavigationItem("5", "Single Entry View"), //todo: nested navigation for viewing single entries
            NavigationItem(AppNavigation.SettingsRoute, "Settings")
        )
    }

    object AppNavigation {
        const val MainRoute = "main"
        const val CreateEntryRoute = "create_entry"
        const val EntryHistoryRoute = "entry_history"
        const val CalendarRoute = "calendar"
        const val DataExportRoute = "data_export"
        const val SettingsRoute = "settings"
    }

    object EntryNavigation {
        const val EntryRoute = "entry"
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
            AppNavigation.CreateEntryRoute -> {
                entryWriting.writeEntryArea(entryRepository)
            }
            AppNavigation.EntryHistoryRoute -> {
                entryHistory.createMainView(entryRepository
                ) { navController.navigate("navigation/5") }
            }
            AppNavigation.CalendarRoute -> {
                // todo: add support for calendar
            }
            AppNavigation.DataExportRoute -> {
                // todo: add support for data export
            }
            "5" -> {
                // todo: nested navigation
                singleEntryView.createSingleEntryView()
            }
            AppNavigation.SettingsRoute -> {
                settingsView.CreateSettingsView(entryRepository)
            }
            else -> {
                Log.e("Route Not found", "Selected route does not exist")
            }
        }
    }
}