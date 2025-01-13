package com.practicum.playlistmaker.domain.storage.interfaces

import com.practicum.playlistmaker.domain.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getTracks(textSearch: String): Flow<SearchResponse>
}