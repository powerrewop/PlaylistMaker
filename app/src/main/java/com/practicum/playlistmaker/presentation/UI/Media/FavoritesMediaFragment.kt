package com.practicum.playlistmaker.presentation.UI.Media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.databinding.FavoritesMediaFragmentBinding
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.presentation.TrackAdapter
import com.practicum.playlistmaker.presentation.ViewModels.Media.FavoritesMediaFragmentViewModel
import com.practicum.playlistmaker.presentation.models.FavLibModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FavoritesMediaFragment : Fragment() {

    private var binding: FavoritesMediaFragmentBinding? = null
    private val viewModel: FavoritesMediaFragmentViewModel by viewModel()

    private lateinit var recycler: RecyclerView

    private val trAdapt: TrackAdapter by inject {
        parametersOf(emptyList<Track>())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)
        binding = FavoritesMediaFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = binding!!.musicList

        trAdapt.isFavForm = true
        recycler.adapter = trAdapt

        recycler.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getFavTracks()

        viewModel.getFavParamModel().observe(viewLifecycleOwner) {
            setVisibility(it)
        }

    }

    fun adapterInit(adapterListTracks: List<Track>?) {
        trAdapt!!.updateTrack(adapterListTracks!!)
        recycler.adapter?.notifyDataSetChanged()
    }

    fun setVisibility(favModel: FavLibModel){
        when (favModel) {
            is FavLibModel.EmptyContent -> emtyContent()
            is FavLibModel.ShowFavLib -> favContent(favModel.trackList)
            is FavLibModel.LoadContent -> loadContent()
        }
    }

    fun emtyContent() {
        binding?.emptyContentLn?.isVisible = true
        binding?.favContentFrame?.isVisible = false
        binding?.favLoadFrame?.isVisible = false
    }

    fun favContent(tracks: List<Track>?) {

        binding?.emptyContentLn?.isVisible = false
        binding?.favContentFrame?.isVisible = true
        binding?.favLoadFrame?.isVisible = false

        adapterInit(tracks)
    }

    fun loadContent() {
        binding?.emptyContentLn?.isVisible = false
        binding?.favContentFrame?.isVisible = false
        binding?.favLoadFrame?.isVisible = true
    }


    companion object {

        fun newInstance() = FavoritesMediaFragment()
    }

}