package com.practicum.playlistmaker.data.storage

import android.content.Intent
import android.net.Uri
import com.google.gson.Gson
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.data.App
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.presentation.UI.MediaActivity
import com.practicum.playlistmaker.presentation.UI.PlayerActivity
import com.practicum.playlistmaker.presentation.UI.SearchActivityNew
import com.practicum.playlistmaker.presentation.UI.SettingsActivity

class IntentWork(private val gson: Gson, private val myApp: App) {

     fun openSend() {
         val actionShare = Intent(Intent.ACTION_SEND)
         actionShare.putExtra(Intent.EXTRA_TEXT, myApp.getString(R.string.link_YP))
         actionShare.type = "text/x-uri"
         val shareLink = Intent.createChooser(actionShare, null)
         shareLink.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         myApp.startActivity(shareLink)
    }

     fun openSendTo() {
         val emailIntent = Intent(Intent.ACTION_SENDTO)
         emailIntent.data = Uri.parse("mailto:")
         emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(myApp.getString(R.string.student_email)))
         emailIntent.putExtra(Intent.EXTRA_TEXT, myApp.getString(R.string.text2))
         emailIntent.putExtra(Intent.EXTRA_SUBJECT, myApp.getString(R.string.text1))
         emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         myApp.startActivity(emailIntent)
    }

     fun openView() {
         val url = Uri.parse(myApp.getString(R.string.legal_YP))
         val intent = Intent(Intent.ACTION_VIEW, url)
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         myApp.startActivity(intent)
    }
     fun openSearch() {
         val intSearch = Intent(myApp, SearchActivityNew::class.java)
         intSearch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         myApp.startActivity(intSearch)
    }

     fun openMedia() {
         val intMA = Intent(myApp, MediaActivity::class.java)
         intMA.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         myApp.startActivity(intMA)
    }

     fun openSettings() {
         val intSettings = Intent(myApp, SettingsActivity::class.java)
         intSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         myApp.startActivity(intSettings)
    }

     fun openPlayer(track: Track) {
         val gsonString = gson.toJson(track)
         val displayIntent = Intent(myApp, PlayerActivity::class.java)
         displayIntent.putExtra("TrackData", gsonString)
         displayIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         myApp.startActivity(displayIntent)
    }
}