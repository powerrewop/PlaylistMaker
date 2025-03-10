package com.practicum.playlistmaker.domain.db

import com.practicum.playlistmaker.domain.model.PlayList
import com.practicum.playlistmaker.domain.model.PlayListTrack
import com.practicum.playlistmaker.domain.usecase.TracksListsInteractor
import kotlinx.coroutines.flow.Flow

class TracksListsInteractorImpl(private val tracksListsRepository: TracksListsRepository): TracksListsInteractor {
    override suspend fun createList(playList: PlayList) {
        tracksListsRepository.createList(playList)
    }

    override suspend fun deleteList(playList: PlayList) {
        tracksListsRepository.deleteList(playList)
    }

    override fun getAllLists(): Flow<List<PlayList>?> {
        return tracksListsRepository.getAllLists()
    }

    override suspend fun addTrackToList(playListTrack: PlayListTrack) {
        tracksListsRepository.addTrackToList(playListTrack)
    }

    override suspend fun deleteTrackFromList(playListTrack: PlayListTrack) {
        tracksListsRepository.deleteTrackFromList(playListTrack)
    }

    override fun getAllTracksThisList(idList: Long): Flow<List<PlayListTrack>?> {
       return tracksListsRepository.getAllTracksThisList(idList)
    }

    override fun getTrackThisList(idList: Long, idTrack: Long): Flow<List<PlayListTrack>?> {
        return tracksListsRepository.getTrackThisList(idList, idTrack)
    }

    override fun getAllTracks(): Flow<List<PlayListTrack>?> {
        return tracksListsRepository.getAllTracks()
    }
}