package com.practicum.playlistmaker.domain.db

import com.practicum.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow


interface FavTrackRepository {

    fun getFavTracks(): Flow<List<Track>?>

   suspend fun saveFavTrack(track: Track)

    fun getAllIdFavtracks(): Flow<List<Long>>

    suspend fun deleteFromFav(track: Track)

}