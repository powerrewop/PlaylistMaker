package com.practicum.playlistmaker.domain.storage.interfaces

import com.practicum.playlistmaker.domain.model.Track

interface SearchRepository {
    fun getTracks(textSearch: String, callback: (Result<List<Track>>) -> Unit)
}