package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.storage.interfaces.MediaPlayerRepository

class MediaplayerUseCase(private val mediaPlayerRepository: MediaPlayerRepository) {
     fun preparePlayer(url: String?, setPlayImage: () -> Unit, setPauseImage: () -> Unit, setTimePlay: (timePlay: String) -> Unit) {
         mediaPlayerRepository.preparePlayer(url, setPlayImage, setPauseImage, setTimePlay)
    }

     fun startPlayer() {
         mediaPlayerRepository.startPlayer()
    }

     fun pausePlayer() {
         mediaPlayerRepository.pausePlayer()
    }

     fun playbackControl() {
         mediaPlayerRepository.playbackControl()
    }

     fun closePlayer() {
         mediaPlayerRepository.closePlayer()
    }
}