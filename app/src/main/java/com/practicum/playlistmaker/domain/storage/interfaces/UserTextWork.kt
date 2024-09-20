package com.practicum.playlistmaker.domain.storage.interfaces

interface UserTextWork {

    fun save(key: String, userText: String)
    fun load(key: String, defText: String): String

}