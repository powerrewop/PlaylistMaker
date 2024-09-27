package com.practicum.playlistmaker.presentation.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.creator.Creator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentInteractor = Creator.getIntentInteractor()

        val btSearch = findViewById<Button>(R.id.bt_search)
        btSearch.setOnClickListener {
            intentInteractor.openSearch()
        }

        val btMedia = findViewById<Button>(R.id.bt_media)
        btMedia.setOnClickListener {
            intentInteractor.openMedia()
        }

        val btSettings = findViewById<Button>(R.id.bt_settings)
        btSettings.setOnClickListener {
            intentInteractor.openSettings()
        }
    }
}