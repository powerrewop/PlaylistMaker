package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.model.SearchResponse
import com.practicum.playlistmaker.domain.storage.interfaces.SearchRepository
import kotlinx.coroutines.flow.Flow

class LoadTracksUseCase(private val searchRepository: SearchRepository) {
    suspend fun load(textSearch: String): Flow<SearchResponse>{
        return searchRepository.getTracks(textSearch)
    }
}