package com.practicum.playlistmaker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tracks_lists_table")
data class TracksLists(
    @PrimaryKey(autoGenerate = true)
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

