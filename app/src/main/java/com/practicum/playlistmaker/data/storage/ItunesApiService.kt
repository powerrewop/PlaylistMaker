package com.practicum.playlistmaker.data.storage

import com.practicum.playlistmaker.data.model.ItunesDataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApiService {
    @GET("search?entity=song")
    fun search(@Query("term") text: String): retrofit2.Call<ItunesDataModel>
}
