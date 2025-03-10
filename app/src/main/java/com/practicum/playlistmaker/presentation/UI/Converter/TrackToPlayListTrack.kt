package com.practicum.playlistmaker.presentation.UI.Converter

import com.practicum.playlistmaker.domain.model.PlayListTrack
import com.practicum.playlistmaker.domain.model.Track

object UiConverter {
    fun TrackToPlayListTrack(idList: Long, track: Track): PlayListTrack {
        return PlayListTrack(
            null,
            track.trackId,
            idList,
            track.trackName,
            track.artistName,
            track.trackTime,
            track.artworkUrl100,
            false,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl
        )
    }

    fun countTracksPlayList(idList: Long, at: List<PlayListTrack>): String{
        var sum: Int = 0

        at.forEach {
            if (it.listId == idList){
                sum++
            }
        }

        return "Треков: $sum"
    }

}