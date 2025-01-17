package com.practicum.playlistmaker.data.db

import com.practicum.playlistmaker.data.db.converters.TrackConverter
import com.practicum.playlistmaker.domain.db.FavTrackRepository
import com.practicum.playlistmaker.domain.model.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FavTrackRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val trackConverter: TrackConverter
): FavTrackRepository {
    override fun getFavTracks(): Flow<List<Track>?> = flow{

        val favTracks = appDatabase.getFavTrackDao().getFavTracks()
        emit(trackConverter.listFavTrackEntityToListTrack(favTracks))

    }.flowOn(Dispatchers.IO)

    override suspend fun saveFavTrack(track: Track) {
        appDatabase.getFavTrackDao().insertTrack(trackConverter.trackToFavTrackEntity(track))
    }

    override fun getAllIdFavtracks(): Flow<List<Long>> = flow{

        val favTracks = appDatabase.getFavTrackDao().getAllIdFavTracks()
        emit(favTracks)

    }.flowOn(Dispatchers.IO)

    override suspend fun deleteFromFav(track: Track) {
        appDatabase.getFavTrackDao().deleteMovieEntity(trackConverter.trackToFavTrackEntity(track))
    }
}