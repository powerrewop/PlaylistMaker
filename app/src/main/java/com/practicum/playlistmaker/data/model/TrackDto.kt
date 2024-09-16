package com.practicum.playlistmaker.data.model

import com.google.gson.annotations.SerializedName

class TrackDto (
    var trackName: String,
    var artistName: String,
    @SerializedName("trackTimeMillis") var trackTime: Int,
    var artworkUrl100: String?,
    var trackId: Long,
    var isHistory: Boolean = false,
    var collectionName: String?,
    var releaseDate: String,
    var primaryGenreName: String,
    var country: String,
    var previewUrl: String?
)