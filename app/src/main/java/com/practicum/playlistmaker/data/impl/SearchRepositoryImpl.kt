package com.practicum.playlistmaker.data.impl

import com.practicum.playlistmaker.data.model.ItunesDataModel
import com.practicum.playlistmaker.data.storage.NetworkClient
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.SearchRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepositoryImpl(
    private val networkClient: NetworkClient
): SearchRepository {
    override fun getTracks(textSearch: String, callback: (Result<List<Track>>) -> Unit) {
        networkClient.getSearchResult(textSearch, object : Callback<ItunesDataModel>{
            override fun onResponse(
                call: Call<ItunesDataModel>,
                response: Response<ItunesDataModel>
            ) {
                val trackDto = response.body()?.results ?: emptyList()
                val tracks = trackDto.map {
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
                        it.previewUrl
                    )
                }
                callback(Result.success(tracks))
            }

            override fun onFailure(call: Call<ItunesDataModel>, t: Throwable) {
                callback(Result.failure(t))
            }

        })
    }

}