package org.kuittaan.voicediary.view

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import org.kuittaan.voicediary.viewmodel.EntryRepository
import java.io.FileDescriptor
import java.io.IOException

class SettingsView: ViewModel() {

    private lateinit var applicationContext: Context // Assuming you set this in your application or wherever appropriate

    private val sharedPreferences by lazy {
        applicationContext.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
    }

    @Composable
    fun CreateSettingsView(entryRepository: EntryRepository) {

        Box {
            val uri: Uri? by rememberUpdatedState(loadImageFromGallery())

            AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )

            Column {
                // todo: set navigation visual
                var checked by remember { (mutableStateOf(true)) }
                Switch(
                    checked = checked, onCheckedChange = {
                        checked = it
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

                if (checked) {
                }
            }
        }
    }

    @Composable
    fun loadImageFromGallery(): Uri? {
        var imageUri by remember {
            mutableStateOf<Uri?>(null)
        }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            // Save the selected image URI to SharedPreferences
            uri?.let {
                imageUri = it
            }
        }

        Button(onClick = {
            // launch gallery selection only for images
            launcher.launch("image/*")
        }) {
            Text(text = "Set background image")
        }

        return imageUri
    }
}