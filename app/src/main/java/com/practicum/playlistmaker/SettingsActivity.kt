package com.practicum.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch


class SettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val myApp = (applicationContext as App)

        val ivBack = findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener {
            finish()
        }

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
        swTheme.isChecked = myApp.darkTheme
        swTheme.setOnCheckedChangeListener { switcher, checked ->

            myApp.switchTheme(checked)
            myApp.sharedPrefs?.edit()
                ?.putBoolean(THEME_SELECT, checked)
                ?.apply()

        }


    }
}