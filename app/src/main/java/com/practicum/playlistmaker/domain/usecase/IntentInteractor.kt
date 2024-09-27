package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.IntentRepository

class IntentInteractor(private val intentRepository: IntentRepository) {
    fun openSend(){
        intentRepository.openSend()
    }
    fun openSendTo(){
        intentRepository.openSendTo()
    }
    fun openView(){
        intentRepository.openView()
    }
    fun openSearch(){
        intentRepository.openSearch()
    }
    fun openMedia(){
        intentRepository.openMedia()
    }
    fun openSettings(){
        intentRepository.openSettings()
    }
    fun openPlayer(track: Track){
        intentRepository.openPlayer(track)
    }
}