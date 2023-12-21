package org.kuittaan.voicediary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.rememberNavController

class ApplicationView {


    /* The onClick parameter doesn't accept a composable function. */

    @Composable
    fun HomeScreen() {
        val database = EntryDatabase.getDatabase(LocalContext.current).dao
        val entryWriting = EntryWriting()
        val entryView = EntryView()

        // Initialize NavController here
        val navController = rememberNavController()

        Column {
            // Double columns so that the modifiers from the second column do not affect the first one.
            Column(
                Modifier.padding(PaddingValues(8.dp, 200.dp, 8.dp, 8.dp))
            ) {

                EntryHistoryButton(navController)

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    CreateEntryButton(navController)
                }

                CalendarButton()
            }

            // Pass NavController to AppNavigation
            AppNavigation(
                navController = navController,
                entryView = entryView,
                entryWriting = entryWriting,
                dao = database
            )
            BottomNavigationBar()
        }
    }

    @Composable
    fun AppNavigation(
        navController: NavHostController,
        entryWriting: EntryWriting,
        entryView: EntryView,
        dao: EntryDao
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                // Content of the "home" destination
                HomeScreen()
            }
            composable("createEntry") {
                // Content of the "createEntry" destination
                entryWriting.writeEntryArea(dao = dao)
            }
            composable("viewEntries") {
                // Content of the "viewEntries" destination
                entryView.createMainView()
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

    @Composable
    fun EntryHistoryButton(
        navController: NavController
    ) {
        var value by remember { mutableStateOf(false) }

        Button(
            onClick = {
                value = true
                if(value == true) {
                    navController.navigate("viewEntries")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)

        ) {
            Text(text = "View past entries")
        }
    }

    @Composable
    fun CreateEntryButton(
        navController: NavController
    ) {
        var value by remember { mutableStateOf(false) }

        Button(
            onClick = {
                value = true
                if(value == true) {
                    navController.navigate("viewEntries")
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.4f)

        ) {
            Text(text = "Create an entry")
        }
    }
    @Composable
    fun CalendarButton() {
        Button(
            onClick = {
                // go to calendar view
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)

        ) {
            Text(text = "To be implemented")
        }
    }
}