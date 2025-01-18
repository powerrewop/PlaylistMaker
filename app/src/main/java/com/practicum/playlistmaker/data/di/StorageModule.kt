package com.practicum.playlistmaker.data.di

import android.app.Application
import android.media.MediaPlayer
import androidx.room.Room
import com.google.gson.Gson
import com.practicum.playlistmaker.data.App
import com.practicum.playlistmaker.data.PLM_PREFERENCES_1
import com.practicum.playlistmaker.data.db.converters.TrackConverter
import com.practicum.playlistmaker.data.db.AppDatabase
import com.practicum.playlistmaker.data.impl.AppThemeRepositoryImpl
import com.practicum.playlistmaker.data.db.FavTrackRepositoryImpl
import com.practicum.playlistmaker.data.impl.HistorySearchRepositoryImpl
import com.practicum.playlistmaker.data.impl.IntentRepositoryImpl
import com.practicum.playlistmaker.data.impl.MediaplayerRepositoryImpl
import com.practicum.playlistmaker.data.impl.ParamDataRepositoryImpl
import com.practicum.playlistmaker.data.impl.SearchRepositoryImpl
import com.practicum.playlistmaker.data.storage.AppTheme
import com.practicum.playlistmaker.data.storage.IntentWork
import com.practicum.playlistmaker.data.storage.ItunesApiService
import com.practicum.playlistmaker.data.storage.MediaPlayerNew
import com.practicum.playlistmaker.data.storage.NetworkClient
import com.practicum.playlistmaker.data.storage.ParamData
import com.practicum.playlistmaker.data.storage.SearchHistory
import com.practicum.playlistmaker.domain.db.FavTrackRepository
import com.practicum.playlistmaker.domain.storage.interfaces.AppThemeRepository
import com.practicum.playlistmaker.domain.storage.interfaces.HistorySearchRepository
import com.practicum.playlistmaker.domain.storage.interfaces.IntentRepository
import com.practicum.playlistmaker.domain.storage.interfaces.MediaPlayerRepository
import com.practicum.playlistmaker.domain.storage.interfaces.ParamDataRepository
import com.practicum.playlistmaker.domain.storage.interfaces.SearchRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val storageModule = module {

    single {
        androidContext().getSharedPreferences(PLM_PREFERENCES_1, Application.MODE_PRIVATE)
    }
    single{
        androidContext().applicationContext as App
    }

    single<ItunesApiService> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ItunesApiService::class.java)
    }

    factory<NetworkClient> {
        NetworkClient(get())
    }

    factory { Gson() }

    factory<MediaPlayer> {
        MediaPlayer()
    }

    factory<AppTheme> {
        AppTheme(get())
    }

    factory<IntentWork> {
        IntentWork(get(), get())
    }

    factory<MediaPlayerNew> {
        MediaPlayerNew(get())
    }

    factory<ParamData> {
        ParamData(get())
    }

    factory<SearchHistory> {
        SearchHistory(get(), get())
    }

    factory<AppThemeRepository> {
        AppThemeRepositoryImpl(get())
    }

    factory<HistorySearchRepository> {
        HistorySearchRepositoryImpl(get())
    }

    factory<IntentRepository> {
        IntentRepositoryImpl(get())
    }

    factory<MediaPlayerRepository> {
        MediaplayerRepositoryImpl(get())
    }

    factory<ParamDataRepository> {
        ParamDataRepositoryImpl(get())
    }

    factory<SearchRepository> {
        SearchRepositoryImpl(get(), get())
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "favtracksdb.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    factory<TrackConverter> {
        TrackConverter()
    }

    single<FavTrackRepository> {
        FavTrackRepositoryImpl(get(), get())
    }
}