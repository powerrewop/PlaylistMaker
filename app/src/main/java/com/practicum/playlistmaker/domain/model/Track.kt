package com.practicum.playlistmaker.domain.model

data class Track(
    var trackName: String,
    var artistName: String,
    var trackTime: Int,
    var artworkUrl100: String?,
    var trackId: Long,
    var isHistory: Boolean = false,
    var collectionName: String?,
    var releaseDate: String?,
    var primaryGenreName: String,
    var country: String,
    var previewUrl: String?
)