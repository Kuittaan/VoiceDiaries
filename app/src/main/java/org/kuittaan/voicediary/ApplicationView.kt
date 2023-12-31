package org.kuittaan.voicediary

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
data class NavigationItem(val id: String, val featureName: String)

class ApplicationView {

    @Composable
    fun HomeScreen() {

        val navController = rememberNavController()
        val navigationItemsViewModel = NavigationItemsViewModel()

        Column {

            NavHost(navController = navController, startDestination = "homeScreen") {
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

            BottomNavigationBar()
        }
    }

    class NavigationItemsViewModel: ViewModel() {
        val navItems = mutableStateListOf(
            NavigationItem("1", "Create Entry"),
            NavigationItem("2", "Show Entry History"),
            NavigationItem("3", "Show Calendar")
        )
    }

    @Composable
    fun NavigationItemDetail(feature: NavigationItem) {

        // UI elements to display the details of the selected feature

        val database = EntryDatabase.getDatabase(LocalContext.current).dao
        val entryWriting = EntryWriting()
        val entryView = EntryView()

        when(feature.id) {
            "1" -> {
                entryWriting.writeEntryArea(dao = database)
            }
            "2" -> {
                entryView.createMainView()
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
            itemsIndexed(navigationItems) { _, navigationItem ->
                // Display each as a clickable item that navigates on-click
                Card(
                    modifier = Modifier
                        .height(60.dp)
                        .width(200.dp)
                        .clickable {
                            navController.navigate("navigation/${navigationItem.id}")
                        }
                    ) {
                    Text(
                        text = navigationItem.featureName
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BottomNavigationBar() {

        // Always visible
        // Show user info, home icon, settings

        Scaffold(
            bottomBar = {
                androidx.compose.material3.NavigationBar(
                    containerColor = colorResource(id = R.color.purple_200),
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
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "Location Icon",
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
                                contentDescription = "Menu Icon",
                                modifier = Modifier.size(42.dp),
                                tint = colorResource(id = R.color.white)
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            // todo: show user info
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Profile Icon",
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