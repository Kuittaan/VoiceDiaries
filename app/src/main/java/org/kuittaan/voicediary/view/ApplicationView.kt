package org.kuittaan.voicediary.view

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import org.kuittaan.voicediary.R
import org.kuittaan.voicediary.viewmodel.Navigator

data class NavigationItem(val id: String, val featureName: String)

class ApplicationView {

    @Composable
    fun AppBackground(selectedImageUri: Uri?) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            selectedImageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                )
            }
        }
    }

    @Composable
    fun HomeScreen() {

        Box {

            val navController = rememberNavController()
            val navigationItemsViewModel = Navigator.NavigationItemsViewModel()
            val navigator = Navigator()

            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = "VoiceDiaries")

                    NavHost(
                        navController = navController,
                        startDestination = "homeScreen"
                    ) {
                        // Elements shown in homescreen
                        composable("homeScreen") {
                            NavigationItemsVisual(
                                navController,
                                navigationItemsViewModel.navItems
                            )
                        }
                        composable("navigation/{navID}") { backStackEntry ->
                            val navID = backStackEntry.arguments?.getString("navID")
                            // Find the nav feature based on it's id
                            val feature = navigationItemsViewModel.navItems.find { it.id == navID }
                            if (feature != null) {
                                // Pass navigation item choice to view
                                navigator.NavigationItemDetail(feature, navController)
                            } else {
                                Log.e("Error", "Nav items not found")
                            }
                        }
                    }
                }

                BottomNavigationBar(
                    navController,
                    navigationItemsViewModel.navItems
                )
            }
        }
    }

    @Composable
    fun NavigationItemsVisual(navController: NavController, navigationItems: List<NavigationItem>) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .clickable {
                        navController.navigate("navigation/${navigationItems[0].id}")
                    }
            ) {
                Text(
                    text = navigationItems[0].featureName,
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentHeight(align = CenterVertically)
                        .padding(20.dp)
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier
                        .fillMaxHeight(0.3f)
                        .weight(1f)
                        .padding(end = 15.dp)
                        .clickable {
                            navController.navigate("navigation/${navigationItems[1].id}")
                        }
                ) {
                    Text(
                        text = navigationItems[1].featureName,
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight(align = CenterVertically)
                            .padding(20.dp)
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxHeight(0.3f)
                        .weight(1f)
                        .padding(start = 15.dp)
                        .clickable {
                            navController.navigate("navigation/${navigationItems[3].id}")
                        }
                ) {
                    Text(
                        text = navigationItems[3].featureName,
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight(align = CenterVertically)
                            .padding(20.dp)
                    )
                }
            }
            // Item 4
            Card(
                modifier = Modifier
                    .fillMaxHeight(0.75f)
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp)
                    .clickable {
                        navController.navigate("navigation/${navigationItems[2].id}")
                    }
            ) {
                Text(
                    text = navigationItems[2].featureName,
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentHeight(align = CenterVertically)
                        .padding(20.dp)
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BottomNavigationBar(navController: NavController, navigationItems: List<NavigationItem>) {

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
                            navController.navigate("navigation/${navigationItems[5].id}")
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
                                imageVector = Icons.Filled.PlayArrow,
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