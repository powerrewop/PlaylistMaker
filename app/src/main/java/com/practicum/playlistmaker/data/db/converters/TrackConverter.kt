package com.practicum.playlistmaker.data.db.converters

import com.practicum.playlistmaker.data.db.entity.FavTrackEntity
import com.practicum.playlistmaker.domain.model.Track

class TrackConverter {

    fun listFavTrackEntityToListTrack(favTrack: List<FavTrackEntity>): List<Track>?{

        return favTrack.map { Track(
            it.trackName,
            it.artistName,
            it.trackTime,
            it.artworkUrl100,
            it.trackId,
            false,
            it.collectionName,
            it.releaseDate,
            it.primaryGenreName,
            it.country,
            it.previewUrl,
            true
        ) }
    }

    fun trackToFavTrackEntity(track: Track): FavTrackEntity{

        return FavTrackEntity(
            track.trackName,
            track.artistName,
            track.trackTime,
            track.artworkUrl100,
            track.trackId,
            false,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
            System.currentTimeMillis())
    }
}