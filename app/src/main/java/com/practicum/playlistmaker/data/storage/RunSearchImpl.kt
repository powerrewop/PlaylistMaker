package com.practicum.playlistmaker.data.storage

import android.os.Handler
import android.os.Looper
import com.practicum.playlistmaker.data.ItunesApiService
import com.practicum.playlistmaker.data.model.TrackDto
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.storage.interfaces.RunSearch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Objects
import java.util.concurrent.Executors

private const val USER_INPUT_DELAY = 2000L

class RunSearchImpl(private val baseUrlIyunes: String): RunSearch {

    lateinit var onBack: (List<Track>?, Int) -> Unit //Тут метод который вызову для передачи результата
    lateinit var onProgressBar: () -> Unit //Тут метод который включит прогресс бар
    //"https://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrlIyunes)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(ItunesApiService::class.java)
    private var tracksDto: List<TrackDto>? = listOf()
    private var tracks: List<Track>? = listOf()
    private val executor = Executors.newCachedThreadPool()
    private val handler = Handler(Looper.getMainLooper())
    private var userTextSearch: String = ""

    private val searchRunnable: Runnable = Runnable {
        executor.execute {
            try {
                onProgressBar.invoke() //включаем прогресс бар
                val resp = iTunesService.search(userTextSearch).execute()
                tracksDto = resp.body()?.results
                tracks = tracksDto?.map {
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
                onBack.invoke(tracks, resp.code()) //передаю результат
            } catch (e: Throwable) {
                onBack.invoke(emptyList(), 400)
            }
        }
    }

    override fun search(searchText: String) {
        userTextSearch = searchText
        if (userTextSearch.length > 0) {
            handler.removeCallbacks(searchRunnable)
            handler.postDelayed(searchRunnable, USER_INPUT_DELAY)
        }
    }
}


