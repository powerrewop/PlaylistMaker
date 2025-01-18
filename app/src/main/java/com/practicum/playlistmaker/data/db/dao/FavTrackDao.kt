package com.practicum.playlistmaker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.playlistmaker.data.db.entity.FavTrackEntity


@Dao
interface FavTrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertTrack(trackEntity: FavTrackEntity)

    @Delete()
     fun deleteMovieEntity(trackEntity: FavTrackEntity)

    @Query("SELECT * FROM fav_tracks_table ORDER BY dateFav DESC")
     fun getFavTracks(): List<FavTrackEntity>

    @Query("SELECT trackId FROM fav_tracks_table")
    fun getAllIdFavTracks(): List<Long>
     
}