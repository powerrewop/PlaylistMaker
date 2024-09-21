package com.practicum.playlistmaker.presentation.activitys

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.creator.SettingsUseCaseCreator

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val ivBack = findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener {
            finish()
        }

        val settingsUseCaseCreator = SettingsUseCaseCreator(context = applicationContext)


        val ivShare = findViewById<ImageView>(R.id.iv_share)
        ivShare.setOnClickListener {
            val actionShare = Intent(Intent.ACTION_SEND)
            actionShare.putExtra(Intent.EXTRA_TEXT, getString(R.string.link_YP))
            actionShare.type = "text/x-uri"
            val shareLink = Intent.createChooser(actionShare, null)
            startActivity(shareLink)
        }

        val ivSupport = findViewById<ImageView>(R.id.iv_support)
        ivSupport.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.student_email)))
            emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.text2))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.text1))
            startActivity(emailIntent)
        }

        val ivAllow = findViewById<ImageView>(R.id.iv_allow)
        ivAllow.setOnClickListener {
            val url = Uri.parse(getString(R.string.legal_YP))
            val intent = Intent(Intent.ACTION_VIEW, url)
            startActivity(intent)
        }

        val swTheme = findViewById<Switch>(R.id.sw_theme)

        val getAppThemeUseCase = settingsUseCaseCreator.getAppThemeUseCase()
        val saveAppThemeUseCase = settingsUseCaseCreator.getSaveAppThemeUseCase()
        val changeAppThemeUseCase = settingsUseCaseCreator.getChangeAppThemeUseCase()
        swTheme.isChecked = getAppThemeUseCase.get()

        swTheme.setOnCheckedChangeListener { switcher, checked ->
            changeAppThemeUseCase.change(checked)
            saveAppThemeUseCase.set(checked)
        }

    }
}