package com.practicum.playlistmaker.domain.model

data class SearchResponse(
    var results: List<Track>?,
    var isError: Boolean,
    var textError: String?
)