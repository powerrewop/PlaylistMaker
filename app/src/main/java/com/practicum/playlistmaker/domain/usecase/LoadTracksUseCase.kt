package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.SearchRepository

class LoadTracksUseCase(private val searchRepository: SearchRepository) {
    fun load(textSearch: String, onSuccess: (List<Track>) -> Unit, onFailure: (Throwable) -> Unit){
        searchRepository.getTracks(textSearch) { result ->
            result.onSuccess(onSuccess)
            result.onFailure(onFailure)
        }
    }
}