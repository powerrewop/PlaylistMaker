package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.storage.interfaces.PlayerControl

class PlayerControlUseCase(private val playerControl: PlayerControl) {

    fun preparePlayer(url: String?){
        playerControl.preparePlayer(url)
    }
    fun startPlayer(){
        playerControl.startPlayer()
    }
    fun pausePlayer(){
        playerControl.pausePlayer()
    }
    fun playbackControl(){
        playerControl.playbackControl()
    }
    fun closePlayer(){
        playerControl.closePlayer()
    }
}