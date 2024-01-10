package org.kuittaan.voicediary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import org.kuittaan.voicediary.model.EntryDatabase
import org.kuittaan.voicediary.view.ApplicationView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val view = ApplicationView()
            view.HomeScreen()

        }
    }
}