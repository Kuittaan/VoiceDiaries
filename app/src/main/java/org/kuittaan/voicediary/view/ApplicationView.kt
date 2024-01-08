package org.kuittaan.voicediary.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.kuittaan.voicediary.model.EntryDatabase
import org.kuittaan.voicediary.R
import org.kuittaan.voicediary.model.EntryDao
import org.kuittaan.voicediary.viewmodel.EntryRepository

data class NavigationItem(val id: String, val featureName: String)

class ApplicationView {

    @Composable
    fun HomeScreen(entryDatabase: EntryDatabase) {

        val navController = rememberNavController()
        val navigationItemsViewModel = NavigationItemsViewModel()

        Column {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "homeScreen"
                ) {
                    composable("homeScreen") { NavigationItemsVisual(navController, navigationItemsViewModel.navItems) }
                    composable("navigation/{navID}") { backStackEntry ->
                        val navID = backStackEntry.arguments?.getString("navID")
                        // Find the nav feature based on it's id
                        val feature = navigationItemsViewModel.navItems.find { it.id == navID }
                        if (feature != null) {
                            // Pass navigation item choice to view
                            NavigationItemDetail(feature)
                        } else {
                            Log.e("Error", "Nav items not found")
                        }
                    }
                }
            }

            BottomNavigationBar(navController)
        }
    }

    class NavigationItemsViewModel: ViewModel() {
        val navItems = mutableStateListOf(
            NavigationItem("1", "Create Entry"),
            NavigationItem("2", "Show Entry History"),
            NavigationItem("3", "Show Calendar"),
            NavigationItem("4", "Data export")
        )
    }

    @Composable
    fun NavigationItemDetail(feature: NavigationItem) {

        // UI elements to display the details of the selected feature

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
            else -> {
                Log.e("Not found", "Feature does not exist")
            }
        }
    }

    @Composable
    fun NavigationItemsVisual(navController: NavController, navigationItems: List<NavigationItem>) {
        LazyColumn {
            item {
                Card(
                    modifier = Modifier
                        .height(60.dp)
                        .width(100.dp)
                        .clickable {
                            navController.navigate("navigation/${navigationItems[0].id}")
                        }
                ) {
                    Text(
                        text = navigationItems[0].featureName
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        modifier = Modifier
                            .height(60.dp)
                            .width(200.dp)
                            .clickable {
                                navController.navigate("navigation/${navigationItems[1].id}")
                            }
                    ) {
                        Text(
                            text = navigationItems[1].featureName
                        )
                    }

                    Card(
                        modifier = Modifier
                            .height(60.dp)
                            .width(200.dp)
                            .clickable {
                                navController.navigate("navigation/${navigationItems[2].id}")
                            }
                    ) {
                        Text(
                            text = navigationItems[2].featureName
                        )
                    }
                }

                // Item 4
                Card(
                    modifier = Modifier
                        .height(60.dp)
                        .width(100.dp)
                        .clickable {
                            navController.navigate("navigation/${navigationItems[3].id}")
                        }
                ) {
                    Text(
                        text = navigationItems[3].featureName
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BottomNavigationBar(navController: NavController) {

        // Always visible
        // Show user info, home icon, settings

        val currentBackStackEntry = navController.currentBackStackEntryAsState().value
        val isRootDestination = currentBackStackEntry?.destination?.route == "your_root_route"

        Scaffold(
            bottomBar = {
                androidx.compose.material3.NavigationBar(
                    containerColor = colorResource(id = R.color.lightred),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp)),
                ) {
                    NavigationBarItem(
                        selected = false,
                        onClick = {

                        },
                        //Other parts will be the same
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = "Settings",
                                modifier = Modifier.size(42.dp),
                                tint = colorResource(id = R.color.white)
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = {

                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Menu,
                                contentDescription = "Menu",
                                modifier = Modifier.size(42.dp),
                                tint = colorResource(id = R.color.white)
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            if (isRootDestination) {
                                // Handle root destination action (e.g., navigate to another destination)
                            } else {
                                // Handle back navigation
                                navController.popBackStack()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Return",
                                modifier = Modifier.size(42.dp),
                                tint = colorResource(id = R.color.white)
                            )
                        }
                    )
                }
            },
            content = { innerPadding ->


                //todo: Insert content here
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = innerPadding)
                ) {

                }
            }
        )
    }
}