@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package org.kuittaan.voicediary

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EntryWriting {
    @Composable
    fun writeEntryArea(dao: EntryDao) {

        var titleText by remember { mutableStateOf("") }
        var contentText by remember { mutableStateOf("") }

        Dialog(onDismissRequest = { /*TODO*/ }) {
            Text(text = "Title")

            TextField(
                value = titleText,
                onValueChange = { titleText = it },
                label = { Text("Entry title") }
            )

            Text(text = "Entry")

            TextField(
                value = contentText,
                onValueChange = { contentText = it },
                label = { Text("Entry content") }
            )

            Button(onClick = {
                //add to database
                addEntry(title = titleText, content = contentText, dao = dao)
            }) {
                Text(text = "Save entry")
            }
        }
    }

    private fun addEntry(title: String, content: String, dao: EntryDao) {
        val entry = Entry(title = title, content = content, id = 0)

        CoroutineScope(Dispatchers.IO).launch {
            dao.insertEntry(entry)
        }
    }
}

