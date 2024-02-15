package org.kuittaan.voicediary.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.kuittaan.voicediary.model.Entry
import org.kuittaan.voicediary.viewmodel.EntryRepository

class EntryHistoryView: ViewModel() {

    @Composable
    fun createMainView(
        entryRepository: EntryRepository,
        navController: NavController
    ) {

        var entries by remember { mutableStateOf<List<Entry>>(emptyList()) }

        LaunchedEffect(entryRepository) {
            entries = entryRepository.getEntriesByDate()
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            for(entry in entries) {
                item {
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .height(100.dp)
                            .clickable {
                                // Open single entry view
                                // todo: make it pass the entry info to navigate to specific entry
                                // https://developer.android.com/jetpack/compose/navigation#nav-with-args?trk=article-ssr-frontend-pulse_x-social-details_comments-action_comment-text
                                navController.navigate("navigation/5")
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Text(
                                text = entry.title,
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .weight(1f),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.width(120.dp))

                            Text(
                                text = entry.id.toString(),
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .weight(1f)
                            )
                        }

                        IconButton(
                            onClick = {
                                viewModelScope.launch {
                                    entryRepository.deleteEntry(entry)
                                    // After deleting, update the entries list
                                    entries = entryRepository.getEntriesByDate()
                                }
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color.Red)
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Delete")
                        }
                    }
                }
            }
        }
    }
}