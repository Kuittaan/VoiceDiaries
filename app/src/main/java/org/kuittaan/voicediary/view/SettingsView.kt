package org.kuittaan.voicediary.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import org.kuittaan.voicediary.model.EntryDatabase
import org.kuittaan.voicediary.viewmodel.EntryRepository

class SettingsView: ViewModel() {

    @Composable
    fun createSettingsView(entryRepository: EntryRepository) {

        Column {

            // todo: set navigation visual

            var checked by remember { (mutableStateOf(true)) }
            Switch(
                checked = checked, onCheckedChange = {checked = it
                }
            )

            Button(onClick = {
                // todo: add warning/confirmation
                viewModelScope.launch {
                    entryRepository.deleteAllEntries()
                }
            }) {
                Text(text = "Delete all entries")
            }

            loadImageFromGallery()
            if (checked) {

            }

        }
    }

    @Composable
    fun loadImageFromGallery() {
        var imageUri by remember {
            mutableStateOf<Uri?>(null)
        }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            imageUri = uri
        }
        Button(onClick = {
            launcher.launch("image/*")
        }) {
            Text(text = "Set background image")
        }
        AsyncImage(
            model = imageUri,
            contentDescription = null,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
        )


    }
}