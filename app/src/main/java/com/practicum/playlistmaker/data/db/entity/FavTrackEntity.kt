package com.practicum.playlistmaker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "fav_tracks_table")
data class FavTrackEntity(
var trackName: String,
var artistName: String,
var trackTime: Int,
var artworkUrl100: String?,

@PrimaryKey(autoGenerate = false)
var trackId: Long,

var isHistory: Boolean = false,
var collectionName: String?,
var releaseDate: String?,
var primaryGenreName: String,
var country: String,
var previewUrl: String?,
var dateFav: Long?
)



