package com.practicum.playlistmaker.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.practicum.playlistmaker.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val useCaseCreator = UseCaseCreator(context = applicationContext)
        val selectIntent = useCaseCreator.getSelectIntentUser()

        val btSearch = findViewById<Button>(R.id.bt_search)
        btSearch.setOnClickListener {
            startActivity(selectIntent.getSearchActivity())
        }

        val btMedia = findViewById<Button>(R.id.bt_media)
        btMedia.setOnClickListener {
            startActivity(selectIntent.getMediaActivity())
        }

        val btSettings = findViewById<Button>(R.id.bt_settings)
        btSettings.setOnClickListener {
            startActivity(selectIntent.getSettingsActivity())
        }
    }
}