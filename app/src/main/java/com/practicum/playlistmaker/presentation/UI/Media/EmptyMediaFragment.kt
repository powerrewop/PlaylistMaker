package com.practicum.playlistmaker.presentation.UI.Media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.EmptyMediaFragmentBinding
import com.practicum.playlistmaker.presentation.ViewModels.Media.EmptyMediaFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmptyMediaFragment : Fragment() {


    private var binding: EmptyMediaFragmentBinding? = null
    private val viewModel: EmptyMediaFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)
        binding = EmptyMediaFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagPosition = requireArguments().getInt(PAGER_POSITION)

        if (pagPosition == 0){
            binding!!.buttonNewPlaylist.isVisible = false
            binding!!.mediaEmptyText.text = getString(R.string.text_media_empty)
        }else{
            binding!!.buttonNewPlaylist.isVisible = true
            binding!!.mediaEmptyText.text = getString(R.string.text_playlist_empty)
        }
    }
    companion object {

        const val PAGER_POSITION = "PAGER_POSITION"
        fun newInstance(pagPosition: Int) = EmptyMediaFragment().apply {
            arguments = bundleOf(PAGER_POSITION to pagPosition)
        }
    }
}