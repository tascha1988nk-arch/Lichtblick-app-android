package de.lichtblickraum.app.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

/** Hintergrundklang einer angeleiteten Übung. Wird in der App erzeugt – keine Dateien, keine Lizenzen. */
enum class Ambience(val label: String) {
    OFF("Stille"),
    PAD("Sanfte Klangfläche"),
    NOISE("Sanftes Rauschen"),
}

object AmbiencePlayer {
    private const val RATE = 22050

    /**
     * Spielt [mode], bis die Coroutine abgebrochen wird. Sanftes Ein- und Ausblenden.
     * Die Klangfläche besteht aus wenigen ruhigen Tönen (A-Dur-Akkord, tief), die langsam an- und abschwellen.
     */
    suspend fun play(mode: Ambience) {
        if (mode == Ambience.OFF) return
        withContext(Dispatchers.Default) {
            val min = AudioTrack.getMinBufferSize(RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT)
            val track = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build(),
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(RATE)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build(),
                )
                .setBufferSizeInBytes(maxOf(min, 8192) * 2)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()
            val chunk = ShortArray(2048)
            val freqs = doubleArrayOf(110.0, 164.81, 220.0, 277.18, 329.63)
            val phases = DoubleArray(freqs.size)
            val lfoOffsets = doubleArrayOf(0.0, 1.3, 2.1, 3.7, 4.4)
            var t = 0L
            var noiseState = 0.0
            var fade = 0.0
            try {
                track.play()
                while (isActive) {
                    for (i in chunk.indices) {
                        val time = t.toDouble() / RATE
                        fade = minOf(1.0, fade + 1.0 / (RATE * 4)) // 4 Sekunden einblenden
                        val sample = when (mode) {
                            Ambience.PAD -> {
                                var v = 0.0
                                for (k in freqs.indices) {
                                    phases[k] += 2 * PI * freqs[k] / RATE
                                    if (phases[k] > 2 * PI) phases[k] -= 2 * PI
                                    val swell = 0.55 + 0.45 * sin(2 * PI * 0.05 * time + lfoOffsets[k])
                                    v += sin(phases[k]) * swell
                                }
                                v / freqs.size * 0.22
                            }
                            Ambience.NOISE -> {
                                val white = Random.nextDouble(-1.0, 1.0)
                                noiseState += 0.02 * (white - noiseState) // weich gefiltert
                                val wave = 0.6 + 0.4 * sin(2 * PI * 0.07 * time)
                                noiseState * 2.2 * wave * 0.5
                            }
                            Ambience.OFF -> 0.0
                        }
                        chunk[i] = (sample * fade * Short.MAX_VALUE).toInt().coerceIn(-32767, 32767).toShort()
                        t++
                    }
                    val written = track.write(chunk, 0, chunk.size)
                    if (written <= 0) delay(50) else yield()
                }
            } finally {
                try {
                    track.pause()
                    track.flush()
                } catch (e: IllegalStateException) {
                }
                track.release()
            }
        }
    }
}
