package com.practicum.playlistmaker.presentation.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivityMediaBinding
import com.practicum.playlistmaker.presentation.ViewModels.MediaViewModel
import com.practicum.playlistmaker.presentation.ViewModelsFactory.MediaViewModelFactory

class MediaActivity : AppCompatActivity() {

    private lateinit var viewModel: MediaViewModel
    private lateinit var binding: ActivityMediaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        viewModel = ViewModelProvider(this, MediaViewModelFactory())[MediaViewModel::class.java]
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}