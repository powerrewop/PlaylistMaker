package com.practicum.playlistmaker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "lists_table")
data class ListsEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long?, //ид самой записи таблицы lists_table

    var name: String,
    var image: String?,
    var desc: String?
)