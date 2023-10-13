package org.kuittaan.voicediary

import android.speech.tts.TextToSpeech;
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

class EntryReading {

    private var tts: TextToSpeech? = null

    @Composable
    fun readEntry() {

        tts = TextToSpeech(LocalContext.current, null)
        tts!!.language = Locale.ENGLISH

    }

}