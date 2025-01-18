package com.practicum.playlistmaker.domain.db

import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.usecase.FavTrackInteractor
import kotlinx.coroutines.flow.Flow

class FavTrackInteractorImpl(private val favTrackRepository: FavTrackRepository): FavTrackInteractor {
    override fun getFavTracks(): Flow<List<Track>?> {
        return favTrackRepository.getFavTracks()
    }

    override suspend fun saveFavTrack(track: Track) {
        favTrackRepository.saveFavTrack(track)
    }

    override fun getAllIdFavTracks(): Flow<List<Long>> {
        return favTrackRepository.getAllIdFavtracks()
    }

    override suspend fun deleteFromFav(track: Track) {
        favTrackRepository.deleteFromFav(track)
    }
}