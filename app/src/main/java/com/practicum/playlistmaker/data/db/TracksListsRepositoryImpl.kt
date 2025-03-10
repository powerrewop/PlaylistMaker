package com.practicum.playlistmaker.data.db

import com.practicum.playlistmaker.data.db.converters.TrackConverter
import com.practicum.playlistmaker.domain.db.TracksListsRepository
import com.practicum.playlistmaker.domain.model.PlayList
import com.practicum.playlistmaker.domain.model.PlayListTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TracksListsRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val trackConverter: TrackConverter
): TracksListsRepository {
    override suspend fun createList(playList: PlayList) {
        appDatabase.getListTrackDao().insertList(trackConverter.playListToListsEntity(playList))
    }

    override suspend fun deleteList(playList: PlayList) {
        appDatabase.getListTrackDao().deleteList(trackConverter.playListToListsEntity(playList))
    }

    override fun getAllLists(): Flow<List<PlayList>?> = flow{
        val allLists = appDatabase.getListTrackDao().getLists()
        emit(trackConverter.listListEntityToListPlayList(allLists))
    }.flowOn(Dispatchers.IO)

    override suspend fun addTrackToList(playListTrack: PlayListTrack) {
        appDatabase.getListTrackDao().insertTrack(trackConverter.playListTrackToTracksLists(playListTrack))
    }

    override suspend fun deleteTrackFromList(playListTrack: PlayListTrack) {
        appDatabase.getListTrackDao().deleteTrack(trackConverter.playListTrackToTracksLists(playListTrack))
    }

    override fun getAllTracksThisList(idList: Long): Flow<List<PlayListTrack>?> = flow{
        val allTracks = appDatabase.getListTrackDao().getTracks(idList)
        emit(trackConverter.listTrackListEntityToPlayListTrack(allTracks))
    }.flowOn(Dispatchers.IO)

    override fun getTrackThisList(idList: Long, idTrack: Long): Flow<List<PlayListTrack>?> = flow{
        val track = appDatabase.getListTrackDao().getTrack(idList, idTrack)
        emit(trackConverter.listTrackListEntityToPlayListTrack(track))
    }.flowOn(Dispatchers.IO)

    override fun getAllTracks(): Flow<List<PlayListTrack>?> = flow{
        val allTracks = appDatabase.getListTrackDao().getAllTracks()
        emit(trackConverter.listTrackListEntityToPlayListTrack(allTracks))
    }.flowOn(Dispatchers.IO)
}