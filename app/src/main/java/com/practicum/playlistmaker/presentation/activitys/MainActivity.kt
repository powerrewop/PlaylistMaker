package com.practicum.playlistmaker.presentation.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.practicum.playlistmaker.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btSearch = findViewById<Button>(R.id.bt_search)
        btSearch.setOnClickListener {
            val intSearch = Intent(this, SearchActivityNew::class.java)
            startActivity(intSearch)
        }

        val btMedia = findViewById<Button>(R.id.bt_media)
        btMedia.setOnClickListener {
            val intMA = Intent(this, MediaActivity::class.java)
            startActivity(intMA)
        }

        val btSettings = findViewById<Button>(R.id.bt_settings)
        btSettings.setOnClickListener {
           val intSettings = Intent(this, SettingsActivity::class.java)
            startActivity(intSettings)
        }
    }
}