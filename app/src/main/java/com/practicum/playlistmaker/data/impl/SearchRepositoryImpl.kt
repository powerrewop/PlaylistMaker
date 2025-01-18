package com.practicum.playlistmaker.data.impl

import com.practicum.playlistmaker.data.db.AppDatabase
import com.practicum.playlistmaker.data.storage.NetworkClient
import com.practicum.playlistmaker.domain.model.SearchResponse
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val appDatabase: AppDatabase
): SearchRepository {

    override suspend fun getTracks(textSearch: String): Flow<SearchResponse> = flow {

        val myResponse = SearchResponse(null, false, "")
        val myResponseDTO = networkClient.getSearchResult(textSearch)

        val favIdList = appDatabase.getFavTrackDao().getAllIdFavTracks()

        myResponse.results = myResponseDTO.results?.map {
            Track(
                it.trackName,
                it.artistName,
                it.trackTime,
                it.artworkUrl100,
                it.trackId,
                it.isHistory,
                it.collectionName,
                it.releaseDate,
                it.primaryGenreName,
                it.country,
                it.previewUrl,
                isFavTrack(it.trackId, favIdList)
            )
        }
        myResponse.isError = myResponseDTO.isError
        myResponse.textError = myResponseDTO.textError

        emit(myResponse)

    }.flowOn(Dispatchers.IO)

    fun isFavTrack(idTrack: Long, favIdList: List<Long>): Boolean{

        favIdList.forEach {
            if (it == idTrack){
                return true
            }
        }

        return false

    }
}