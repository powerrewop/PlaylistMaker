package com.practicum.playlistmaker.domain.usecase

import com.practicum.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow


interface FavTrackInteractor {
    fun getFavTracks(): Flow<List<Track>?>

    suspend fun saveFavTrack(track: Track)

    fun getAllIdFavTracks(): Flow<List<Long>>

    suspend fun deleteFromFav(track: Track)
}