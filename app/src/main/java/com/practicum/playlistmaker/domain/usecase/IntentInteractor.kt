package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.IntentRepository

class IntentInteractor(private val intentRepository: IntentRepository) {

    var callBack: ((idTrack: Long, newFav: Boolean) -> Unit)? = null

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

    fun updateFav(idTrack: Long, newFav: Boolean){
        callBack?.invoke(idTrack, newFav)
    }
}