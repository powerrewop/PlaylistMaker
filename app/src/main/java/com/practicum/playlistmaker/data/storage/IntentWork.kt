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
import org.koin.java.KoinJavaComponent.getKoin

class IntentWork() {

     fun openSend() {
         val actionShare = Intent(Intent.ACTION_SEND)
         actionShare.putExtra(Intent.EXTRA_TEXT, App.instance.getString(R.string.link_YP))
         actionShare.type = "text/x-uri"
         val shareLink = Intent.createChooser(actionShare, null)
         shareLink.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         App.instance.startActivity(shareLink)
    }

     fun openSendTo() {
         val emailIntent = Intent(Intent.ACTION_SENDTO)
         emailIntent.data = Uri.parse("mailto:")
         emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(App.instance.getString(R.string.student_email)))
         emailIntent.putExtra(Intent.EXTRA_TEXT, App.instance.getString(R.string.text2))
         emailIntent.putExtra(Intent.EXTRA_SUBJECT, App.instance.getString(R.string.text1))
         emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         App.instance.startActivity(emailIntent)
    }

     fun openView() {
         val url = Uri.parse(App.instance.getString(R.string.legal_YP))
         val intent = Intent(Intent.ACTION_VIEW, url)
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         App.instance.startActivity(intent)
    }
     fun openSearch() {
         val intSearch = Intent(App.instance, SearchActivityNew::class.java)
         intSearch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         App.instance.startActivity(intSearch)
    }

     fun openMedia() {
         val intMA = Intent(App.instance, MediaActivity::class.java)
         intMA.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         App.instance.startActivity(intMA)
    }

     fun openSettings() {
         val intSettings = Intent(App.instance, SettingsActivity::class.java)
         intSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         App.instance.startActivity(intSettings)
    }

     fun openPlayer(track: Track) {
         val gson: Gson  = getKoin().get()
         val gsonString = gson.toJson(track)
         val displayIntent = Intent(App.instance, PlayerActivity::class.java)
         displayIntent.putExtra("TrackData", gsonString)
         displayIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         App.instance.startActivity(displayIntent)
    }
}