package com.practicum.playlistmaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.playlistmaker.data.db.dao.FavTrackDao
import com.practicum.playlistmaker.data.db.entity.FavTrackEntity

@Database(version = 2, entities = [FavTrackEntity::class])
abstract class AppDatabase : RoomDatabase(){

    abstract fun getFavTrackDao(): FavTrackDao

}