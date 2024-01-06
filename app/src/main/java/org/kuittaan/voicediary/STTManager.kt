package org.kuittaan.voicediary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.SpeechRecognizer
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import java.util.Locale

// TODO: handle RECORD_AUDIO permission
class STTManager(context: Context) {

    // TODO: Update to API31 from API30 to use createOnDeviceSpeechRecognizer instead
    private val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    private val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

    private val recognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(p0: Bundle?) {
            TODO("Not yet implemented")
        }

        override fun onBeginningOfSpeech() {
            TODO("Not yet implemented")
        }

        override fun onRmsChanged(p0: Float) {
            TODO("Not yet implemented")
        }

        override fun onBufferReceived(p0: ByteArray?) {
            TODO("Not yet implemented")
        }

        override fun onEndOfSpeech() {
            TODO("Not yet implemented")
        }

        override fun onError(p0: Int) {
            TODO("Not yet implemented")
        }

        override fun onResults(results: Bundle?) {
            val recognizedText = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0)
             //TODO: Do something with the final recognized text
        }

        override fun onPartialResults(p0: Bundle?) {
            TODO("Not yet implemented")
        }

        override fun onEvent(p0: Int, p1: Bundle?) {
            TODO("Not yet implemented")
        }
    }

    fun init() {
        speechRecognizer.setRecognitionListener(recognitionListener)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().toString())
        //speechRecognizer.startListening(recognizerIntent)
        //speechRecognizer.stopListening()
        //speechRecognizer.destroy()
    }

}