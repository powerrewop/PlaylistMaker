package com.practicum.playlistmaker.presentation.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.creator.Creator

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val intentInteractor = Creator.getIntentInteractor()

        val ivBack = findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener {
            finish()
        }

        val ivShare = findViewById<ImageView>(R.id.iv_share)
        ivShare.setOnClickListener {
            intentInteractor.openSend()
        }

        val ivSupport = findViewById<ImageView>(R.id.iv_support)
        ivSupport.setOnClickListener {
            intentInteractor.openSendTo()
        }

        val ivAllow = findViewById<ImageView>(R.id.iv_allow)
        ivAllow.setOnClickListener {
            intentInteractor.openView()
        }

        val swTheme = findViewById<Switch>(R.id.sw_theme)

        val appThemeInteractor = Creator.getAppThemeInteractor()

        swTheme.isChecked = appThemeInteractor.getTheme()

        swTheme.setOnCheckedChangeListener { switcher, checked ->
            appThemeInteractor.changeTheme(checked)
            appThemeInteractor.setTheme(checked)
        }
    }
}