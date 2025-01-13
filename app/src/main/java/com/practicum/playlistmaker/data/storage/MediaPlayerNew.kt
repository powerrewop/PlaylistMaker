package com.practicum.playlistmaker.data.storage

import android.media.MediaPlayer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


class MediaPlayerNew(private val mediaPlayer: MediaPlayer) {

    private var playerState = STATE_DEFAULT

    private lateinit var updJob: Job

    lateinit var setPlImage: () -> Unit
    lateinit var setPaImage: () -> Unit
    lateinit var setTime: (timePlay: String) -> Unit
    fun preparePlayer(url: String?, setPlayImage: () -> Unit, setPauseImage: () -> Unit, setTimePlay: (timePlay: String) -> Unit) {
        setPlImage = setPlayImage
        setPaImage = setPauseImage
        setTime = setTimePlay

        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = STATE_PREPARED
        }
    }

    fun startPlayer() {
        mediaPlayer.start()
        playerState = STATE_PLAYING

        updJob =   GlobalScope.launch {
            createUpdateTimerTask()
        }

        setPaImage.invoke()
    }

    fun pausePlayer() {

        if (playerState == STATE_PLAYING) {
            mediaPlayer.pause()
            playerState = STATE_PAUSED
            setPlImage.invoke()
        }
    }

    fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    fun closePlayer() {
        mediaPlayer.release()
    }

       private suspend fun createUpdateTimerTask() {

            val timeTrack = mediaPlayer.currentPosition

            mediaPlayer.setOnCompletionListener {
                pausePlayer()
                updJob.cancel()
                setTime.invoke("00:00")
            }

            if (playerState == STATE_PLAYING) {
                setTime.invoke(SimpleDateFormat("mm:ss", Locale.getDefault()).format(timeTrack))

                updJob = GlobalScope.launch {
                    delay(TIMER_DELAY)
                    createUpdateTimerTask()
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