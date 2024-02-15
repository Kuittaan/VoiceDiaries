@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package org.kuittaan.voicediary.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.kuittaan.voicediary.model.Entry
import org.kuittaan.voicediary.model.STTManager
import org.kuittaan.voicediary.viewmodel.EntryRepository

class EntryCreateView: ViewModel() {
    @Composable
    fun writeEntryArea(
        entryRepository: EntryRepository
    ) {

        var titleText by remember { mutableStateOf("") }
        var contentText by remember { mutableStateOf("") }
        val context = LocalContext.current

        Column (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = titleText,
                singleLine = true,
                onValueChange = {
                    // Max length of 50 characters
                    if (it.length <= 50) {
                        titleText = it
                    } else {
                        it.substring(0, 50)
                    }
                },
                label = { Text("Entry title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = contentText,
                onValueChange = { contentText = it },
                label = { Text("Entry content") },
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(20.dp)
            )

            Row {
                Button(onClick = {
                    // add to database
                    viewModelScope.launch {
                        if(!entryRepository.insertEntry(Entry(0, titleText, contentText))) {
                            //if inserting into database fails
                            // todo: show error message to user
                            Log.d("fail", "insert fail")
                        } else {
                            // if succeed in inserting to database
                            titleText = ""
                            contentText = ""
                        }
                    }
                    // Empty the fields
                }) {
                    Text(text = "Save entry")
                }

                // todo: set the other button to right edge
                Spacer(modifier = Modifier.width(100.dp))

                val sttManager = STTManager(context)
                LaunchedEffect(sttManager) {
                    sttManager.collect { recognizedText ->
                        contentText.value = recognizedText
                    }
                }
                Button(onClick = {

                }) {
                    Text(text = "Record voice")
                }

            }
        }
    }
}

