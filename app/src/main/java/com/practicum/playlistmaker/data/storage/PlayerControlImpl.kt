package com.practicum.playlistmaker.data.storage

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.practicum.playlistmaker.domain.storage.interfaces.PlayerControl
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerControlImpl: PlayerControl {

    lateinit var onPlayImage: () -> Unit
    lateinit var onPauseImage: () -> Unit
    lateinit var onSetTimer: (timeText: String) -> Unit

    private val mediaPlayer = MediaPlayer()
    private var playerState = STATE_DEFAULT
    private var mainThreadHandler = Handler(Looper.getMainLooper())
    override fun preparePlayer(url: String?) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = STATE_PREPARED
        }
    }

    override fun startPlayer() {
        mediaPlayer.start()
        playerState = STATE_PLAYING
        mainThreadHandler.post(createUpdateTimerTask())
        onPauseImage.invoke()
    }

    override fun pausePlayer() {
        mediaPlayer.pause()
        playerState = STATE_PAUSED
        onPlayImage.invoke()
    }

    override fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    override fun closePlayer() {
        mediaPlayer.release()
    }

    private fun createUpdateTimerTask(): Runnable {
        return object : Runnable {
            override fun run() {

                val timeTrack = mediaPlayer.currentPosition

                mediaPlayer.setOnCompletionListener {
                    pausePlayer()
                    mainThreadHandler.removeCallbacks(this)
                    onSetTimer.invoke("00:00")
                }

                if (playerState == STATE_PLAYING) {
                    onSetTimer.invoke(SimpleDateFormat("mm:ss", Locale.getDefault()).format(timeTrack))
                    mainThreadHandler.postDelayed(this, TIMER_DELAY)
                }
            }
        }
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val TIMER_DELAY = 250L
    }
}