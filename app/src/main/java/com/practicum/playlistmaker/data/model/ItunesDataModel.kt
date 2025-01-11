package com.practicum.playlistmaker.data.model

data class ItunesDataModel(
    val results: List<TrackDto>?,
    var isError: Boolean,
    var textError: String?
)


