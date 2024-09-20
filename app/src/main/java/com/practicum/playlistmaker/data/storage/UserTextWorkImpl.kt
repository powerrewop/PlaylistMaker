package com.practicum.playlistmaker.data.storage

import android.os.Bundle
import com.practicum.playlistmaker.domain.storage.interfaces.UserTextWork

class UserTextWorkImpl: UserTextWork {

    lateinit var bundle: Bundle
    override fun save(key: String, userText: String) {
        bundle.putString(key, userText)
    }

    override fun load(key: String, defText: String): String {
        return bundle.getString(key, defText)
    }
}