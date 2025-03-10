package com.practicum.playlistmaker.domain.model

class PlayListTrack(
    var id: Long?, //ид самой записи таблицы tracks_lists_table
    var trackId: Long, //ид трека который с апи приходит
    var listId: Long, //ид плей-листа

    var trackName: String,
    var artistName: String,
    var trackTime: Int,
    var artworkUrl100: String?,
    var isHistory: Boolean = false,
    var collectionName: String?,
    var releaseDate: String?,
    var primaryGenreName: String,
    var country: String,
    var previewUrl: String?,
)