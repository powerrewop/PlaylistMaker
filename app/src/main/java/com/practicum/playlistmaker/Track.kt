package com.practicum.playlistmaker

import com.google.gson.annotations.SerializedName

data class Track(
    var trackName: String,
    var artistName: String,
    @SerializedName("trackTimeMillis") var trackTime: Int,
    var artworkUrl100: String?,
    var trackId: Long,
    var isHistory: Boolean = false,
    var collectionName: String?,
    var releaseDate: String,
    var primaryGenreName: String,
    var country: String
)