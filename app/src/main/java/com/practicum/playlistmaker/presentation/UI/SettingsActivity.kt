package com.practicum.playlistmaker.presentation.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivitySettingsBinding
import com.practicum.playlistmaker.presentation.ViewModels.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {

    private val viewModel: SettingsViewModel by viewModel()
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

       val ivBack = binding.ivBack
        ivBack.setOnClickListener {
            finish()
        }

        val ivShare = binding.ivShare
        ivShare.setOnClickListener {
            viewModel.shareClick()
        }

        val ivSupport = binding.ivSupport
        ivSupport.setOnClickListener {
            viewModel.supportClick()
        }

        val ivAllow = binding.ivAllow
        ivAllow.setOnClickListener {
            viewModel.allowClick()
        }

        val swTheme = binding.swTheme

        viewModel.getIsDarkTheme().observe(this) {
            swTheme.isChecked = it
        }

        swTheme.setOnCheckedChangeListener { switcher, checked ->
            viewModel.setThemeClick(checked)
        }
    }
}