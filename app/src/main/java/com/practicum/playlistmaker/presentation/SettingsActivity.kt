package com.practicum.playlistmaker.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import com.practicum.playlistmaker.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val ivBack = findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener {
            finish()
        }

        val useCaseCreator = UseCaseCreator(context = applicationContext)
        val selectIntent = useCaseCreator.getSelectIntentUser()

        val ivShare = findViewById<ImageView>(R.id.iv_share)
        ivShare.setOnClickListener {
            startActivity(selectIntent.getSend())
        }

        val ivSupport = findViewById<ImageView>(R.id.iv_support)
        ivSupport.setOnClickListener {
            startActivity(selectIntent.getSendTo())
        }

        val ivAllow = findViewById<ImageView>(R.id.iv_allow)
        ivAllow.setOnClickListener {
            startActivity(selectIntent.getView())
        }

        val swTheme = findViewById<Switch>(R.id.sw_theme)

        val getAppThemeUseCase = useCaseCreator.getAppThemeUseCase()
        val saveAppThemeUseCase = useCaseCreator.getSaveAppThemeUseCase()
        val changeAppThemeUseCase = useCaseCreator.getChangeAppThemeUseCase()
        swTheme.isChecked = getAppThemeUseCase.get()

        swTheme.setOnCheckedChangeListener { switcher, checked ->
            changeAppThemeUseCase.change(checked)
            saveAppThemeUseCase.set(checked)
        }

    }
}