package de.lichtblickraum.app.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import java.util.Locale

/**
 * Vorlesestimme für angeleitete Übungen – nutzt die Sprachausgabe des Geräts
 * (offline, kostenlos, keine Daten verlassen das Handy).
 */
class GuidedVoice(context: Context) : TextToSpeech.OnInitListener {

    /** null = wird vorbereitet, true = bereit, false = auf diesem Gerät nicht verfügbar */
    var ready by mutableStateOf<Boolean?>(null)
        private set

    @Volatile private var pending: CompletableDeferred<Unit>? = null
    private val tts = TextToSpeech(context.applicationContext, this)

    override fun onInit(status: Int) {
        if (status != TextToSpeech.SUCCESS) {
            ready = false
            return
        }
        val result = tts.setLanguage(Locale.GERMANY)
        ready = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
        tts.setSpeechRate(0.82f)
        tts.setPitch(0.95f)
        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) = Unit
            override fun onDone(utteranceId: String?) { pending?.complete(Unit) }
            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) { pending?.complete(Unit) }
            override fun onStop(utteranceId: String?, interrupted: Boolean) { pending?.complete(Unit) }
        })
    }

    /** Liest [text] vor und kehrt zurück, wenn fertig gesprochen wurde. */
    suspend fun speak(text: String) {
        if (ready != true) return
        val done = CompletableDeferred<Unit>()
        pending = done
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "lichtblick-${System.nanoTime()}")
        try {
            done.await()
        } catch (e: CancellationException) {
            tts.stop()
            throw e
        }
    }

    fun stop() {
        tts.stop()
        pending?.complete(Unit)
    }

    fun shutdown() {
        stop()
        tts.shutdown()
    }
}
