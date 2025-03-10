package com.practicum.playlistmaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.playlistmaker.data.db.dao.FavTrackDao
import com.practicum.playlistmaker.data.db.dao.ListTrackDao
import com.practicum.playlistmaker.data.db.entity.FavTrackEntity
import com.practicum.playlistmaker.data.db.entity.ListsEntity
import com.practicum.playlistmaker.data.db.entity.TracksLists

@Database(version = 5, entities = [FavTrackEntity::class, ListsEntity::class, TracksLists::class])
abstract class AppDatabase : RoomDatabase(){

    abstract fun getFavTrackDao(): FavTrackDao
    abstract fun getListTrackDao(): ListTrackDao

}