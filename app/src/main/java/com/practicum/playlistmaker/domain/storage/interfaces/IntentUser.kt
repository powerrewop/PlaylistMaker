package com.practicum.playlistmaker.domain.storage.interfaces

import android.content.Intent

interface IntentUser
{
    fun getSend(): Intent
    fun getSendTo(): Intent
    fun getView(): Intent
    fun getSearchActivity(): Intent
    fun getMediaActivity(): Intent
    fun getSettingsActivity(): Intent
}