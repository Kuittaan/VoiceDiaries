package org.kuittaan.voicediary.model

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TTSProcessor(private val context: Context) {

    private var textToSpeech: TextToSpeech? = null

    fun init() {
        textToSpeech = TextToSpeech(context, object : TextToSpeech.OnInitListener {
            override fun onInit(status: Int) {
                if (status == TextToSpeech.SUCCESS) {
                    val result = textToSpeech?.setLanguage(Locale.getDefault())
                    if(result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("Error", "Language not supported")
                    }
                }
            }

            fun speak(text: String) {
                textToSpeech?.speak(
                    text,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    null)
            }

            fun stopSpeaking() {
                textToSpeech?.stop()
            }

            fun destroy() {
                textToSpeech?.shutdown()
            }
        })
    }
}