package com.practicum.playlistmaker.presentation.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivityMainBinding
import com.practicum.playlistmaker.presentation.ViewModels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btSearch = binding.btSearch
        btSearch.setOnClickListener {
            viewModel.openSearch()
        }

        val btMedia = binding.btMedia
        btMedia.setOnClickListener {
            viewModel.openMedia()
        }

        val btSettings = binding.btSettings
        btSettings.setOnClickListener {
            viewModel.openSettings()
        }
    }
}