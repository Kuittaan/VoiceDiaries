@file:OptIn(ExperimentalMaterial3Api::class)

package org.kuittaan.voicediary

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room


@Composable
fun writeEntryArea() {

    //TODO use dialog to show all of this as a popup?

    var titleText by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }

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
    /*TODO add to database*/
    }) {
        Text(text = "Save entry")
    }

}