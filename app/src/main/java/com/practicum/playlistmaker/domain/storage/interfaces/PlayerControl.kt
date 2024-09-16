package com.practicum.playlistmaker.domain.storage.interfaces

interface PlayerControl {
    fun preparePlayer(url: String?)
    fun startPlayer()
    fun pausePlayer()
    fun playbackControl()
    fun closePlayer()
}