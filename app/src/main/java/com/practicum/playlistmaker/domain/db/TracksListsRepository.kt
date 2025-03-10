package com.practicum.playlistmaker.domain.db

import com.practicum.playlistmaker.domain.model.PlayList
import com.practicum.playlistmaker.domain.model.PlayListTrack
import kotlinx.coroutines.flow.Flow


interface TracksListsRepository {

    suspend fun createList(playList: PlayList)
    suspend fun deleteList(playList: PlayList)

    fun getAllLists(): Flow<List<PlayList>?>

    suspend fun addTrackToList(playListTrack: PlayListTrack)
    suspend fun deleteTrackFromList(playListTrack: PlayListTrack)

    fun getAllTracksThisList(idList: Long): Flow<List<PlayListTrack>?>

    fun getTrackThisList(idList: Long, idTrack: Long): Flow<List<PlayListTrack>?>

    fun getAllTracks(): Flow<List<PlayListTrack>?>

}