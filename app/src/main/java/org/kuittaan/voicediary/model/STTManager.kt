package org.kuittaan.voicediary.model

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.SpeechRecognizer
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContentProviderCompat.requireContext
import java.util.Locale

// TODO: handle RECORD_AUDIO permission
class STTManager {

    companion object {

        private val _recognizedText = mutableStateOf("")
        val recognizedText: String get() = _recognizedText.value

        fun startVoiceInput(context: Context) {
            // todo: ask for permission
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Recording voice ...")

            val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(p0: Bundle?) {
                }

                override fun onBeginningOfSpeech() {
                    // todo: display start of speaking in ui
                }

                override fun onRmsChanged(p0: Float) {
                }

                override fun onBufferReceived(p0: ByteArray?) {
                }

                override fun onEndOfSpeech() {
                    // todo: display end of speech in ui
                }

                override fun onError(error: Int) {
                    Log.e("STT", "error: $error")
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if(!matches.isNullOrEmpty()) {
                        val text = matches[0]
                        _recognizedText.value = text

                    }
                }

                override fun onPartialResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (!matches.isNullOrEmpty()) {
                        val text = matches[0]
                        _recognizedText.value = text
                    }
                }

                override fun onEvent(p0: Int, p1: Bundle?) {
                }
            })
        }
    }
}