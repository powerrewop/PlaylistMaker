package com.practicum.playlistmaker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.playlistmaker.data.db.entity.ListsEntity
import com.practicum.playlistmaker.data.db.entity.TracksLists


@Dao
interface ListTrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(listsEntity: ListsEntity)

    @Delete()
    suspend fun deleteList(listsEntity: ListsEntity)

    @Query("SELECT * FROM lists_table")
    fun getLists(): List<ListsEntity>

    @Query("SELECT * FROM lists_table WHERE id = :idList")
    fun getList(idList: Long): ListsEntity


    /////////////////////
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(tracksLists: TracksLists)

    @Delete()
    suspend fun deleteTrack(tracksLists: TracksLists)

    @Query("SELECT * FROM tracks_lists_table WHERE listId = :idList ORDER BY dateAdd DESC")
    fun getTracks(idList: Long): List<TracksLists>

    @Query("SELECT * FROM tracks_lists_table WHERE listId = :idList AND trackId = :idTrack")
    fun getTrack(idList: Long, idTrack: Long): List<TracksLists>

    @Query("SELECT * FROM tracks_lists_table")
    fun getAllTracks(): List<TracksLists>

    @Query("SELECT SUM(trackTime) FROM tracks_lists_table WHERE listId = :idList")
    suspend fun getAllTimeList(idList: Long): Int?

}