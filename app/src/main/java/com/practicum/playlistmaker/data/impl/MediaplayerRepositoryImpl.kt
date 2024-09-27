package com.practicum.playlistmaker.data.impl

import com.practicum.playlistmaker.data.storage.MediaPlayerNew
import com.practicum.playlistmaker.domain.storage.interfaces.MediaPlayerRepository

class MediaplayerRepositoryImpl(private val mediaPlayerNew: MediaPlayerNew): MediaPlayerRepository {
    override fun preparePlayer(url: String?, setPlayImage: () -> Unit, setPauseImage: () -> Unit, setTimePlay: (timePlay: String) -> Unit) {
        mediaPlayerNew.preparePlayer(url, setPlayImage, setPauseImage, setTimePlay)
    }

    override fun startPlayer() {
        mediaPlayerNew.startPlayer()
    }

    override fun pausePlayer() {
        mediaPlayerNew.pausePlayer()
    }

    override fun playbackControl() {
        mediaPlayerNew.playbackControl()
    }

    override fun closePlayer() {
        mediaPlayerNew.closePlayer()
    }
}