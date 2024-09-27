package com.practicum.playlistmaker.domain.storage.interfaces

interface MediaPlayerRepository {
    fun preparePlayer(url: String?, setPlayImage: () -> Unit, setPauseImage: () -> Unit, setTimePlay: (timePlay: String) -> Unit)
    fun startPlayer()
    fun pausePlayer()
    fun playbackControl()
    fun closePlayer()
}