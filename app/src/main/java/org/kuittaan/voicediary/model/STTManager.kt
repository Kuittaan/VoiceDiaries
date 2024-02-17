package org.kuittaan.voicediary.model

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.SpeechRecognizer
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import java.util.Locale

// TODO: handle RECORD_AUDIO permission
class STTManager {

    private val _recognizedText = mutableStateOf("")
    val recognizedText: String get() = _recognizedText.value

    fun startVoiceInput(context: Context, activity: Activity) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                activity,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                1
            )
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Recording voice ...")

        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {
                speechRecognizer?.startListening(intent)
            }

            override fun onBeginningOfSpeech() {
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
                Log.d("a", "$_recognizedText")
            }

            override fun onPartialResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val text = matches[0]
                    _recognizedText.value = text
                    Log.d("a", "$_recognizedText")
                }
            }

            override fun onEvent(p0: Int, p1: Bundle?) {
            }
        })
        speechRecognizer?.startListening(intent)
    }
}