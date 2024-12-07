package com.practicum.playlistmaker.presentation.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.practicum.playlistmaker.databinding.SettingsFragmentBinding
import com.practicum.playlistmaker.presentation.ViewModels.SettingsFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private var binding: SettingsFragmentBinding? = null
    private val viewModel: SettingsFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivShare = binding?.ivShare
        ivShare?.setOnClickListener {
            viewModel.shareClick()
        }

        val ivSupport = binding?.ivSupport
        ivSupport?.setOnClickListener {
            viewModel.supportClick()
        }

        val ivAllow = binding?.ivAllow
        ivAllow?.setOnClickListener {
            viewModel.allowClick()
        }

        val swTheme = binding?.swTheme

        viewModel.getIsDarkTheme().observe(viewLifecycleOwner) {
            swTheme?.isChecked = it
        }

        swTheme?.setOnCheckedChangeListener { switcher, checked ->
            viewModel.setThemeClick(checked)
        }

    }
}