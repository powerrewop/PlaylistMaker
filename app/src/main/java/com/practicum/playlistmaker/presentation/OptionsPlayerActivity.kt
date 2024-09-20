package com.practicum.playlistmaker.presentation

import android.icu.text.SimpleDateFormat
import android.widget.TextView
import androidx.core.view.isVisible
import java.util.Locale

class OptionsPlayerActivity {
    fun setVisibility(tv_trackAlbum: TextView, tv_trackAlbumHead: TextView, tv_trackAlbumInfo: TextView, collectionName: String?){

        if (collectionName?.isEmpty() == true) {
            tv_trackAlbum.isVisible = false
            tv_trackAlbumHead.isVisible = false
            tv_trackAlbumInfo.isVisible = false
        } else {
            tv_trackAlbum.isVisible = true
            tv_trackAlbumHead.isVisible = true
            tv_trackAlbumInfo.isVisible = true
        }
    }

    fun getTrackTime(timeTrack: Int): String{
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(timeTrack)
    }

    fun getYear(date: String?): String{
        if (date != null) {
            return date.substring(0, 4)
        }
        return ""
    }
}