package org.kuittaan.voicediary.model

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.SpeechRecognizer
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

// TODO: handle RECORD_AUDIO permission
class STTManager(context: Context) {

    private val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    private val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

    private val recognitionListener = object : RecognitionListener {
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
                // todo: display text in ui
            }
        }

        override fun onPartialResults(results: Bundle?) {
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            if (!matches.isNullOrEmpty()) {
                val text = matches[0]
                // todo: display text in ui
            }
        }

        override fun onEvent(p0: Int, p1: Bundle?) {
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