package com.practicum.playlistmaker.presentation.models
data class TrackParamModel(
    val artworkUrl100: String?,
    val trackName: String,
    val collectionName: String?,
    val trackTime: String,
    val releaseDate: String?,
    val primaryGenreName: String,
    val country: String,
    var timerText: String,
    val trackAlbumVisible: Boolean,
    var isPlay: Boolean
)