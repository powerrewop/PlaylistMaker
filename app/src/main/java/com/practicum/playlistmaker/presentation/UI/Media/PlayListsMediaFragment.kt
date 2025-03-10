package com.practicum.playlistmaker.presentation.UI.Media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.PlaylistsMediaFragmentBinding
import com.practicum.playlistmaker.domain.model.PlayList
import com.practicum.playlistmaker.presentation.ListsAdapter
import com.practicum.playlistmaker.presentation.ViewModels.Media.PlayListsMediaFragmentViewModel
import com.practicum.playlistmaker.presentation.models.PlayListMediaFragmentModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlayListsMediaFragment : Fragment() {


    private var binding: PlaylistsMediaFragmentBinding? = null
    private val viewModel: PlayListsMediaFragmentViewModel by viewModel()

    private lateinit var recycler: RecyclerView

    private val trAdapt: ListsAdapter by inject {
        parametersOf(emptyList<PlayList>())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)
        binding = PlaylistsMediaFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getListsModel().observe(viewLifecycleOwner) {

            when (it) {
                is PlayListMediaFragmentModel.emptyContent -> emptyContent()
                is PlayListMediaFragmentModel.showPlayLists -> showContent(it)
                is PlayListMediaFragmentModel.loadContent -> loadContent()
            }
        }

        recycler = binding!!.rv
        recycler.adapter = trAdapt
        recycler.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        binding!!.buttonNewPlaylist.setOnClickListener {
            findNavController().navigate(R.id.action_parrentMediaFragment_to_createListFragment)
        }

    }

    override fun onResume() {
        super.onResume()

        viewModel.readPlayLists()
    }

    fun adapterInit(listLists: List<PlayList>) {
        trAdapt.updateLists(listLists)
        recycler.adapter?.notifyDataSetChanged()
    }

    fun emptyContent(){

        binding!!.mediaEmptyText.isVisible = true
        binding!!.imageEmpty.isVisible = true
        binding!!.rv.isVisible = false
        binding!!.progressBarLayout.isVisible = false
    }

    fun loadContent(){

        binding!!.mediaEmptyText.isVisible = false
        binding!!.imageEmpty.isVisible = false
        binding!!.rv.isVisible = false
        binding!!.progressBarLayout.isVisible = true
    }

    fun showContent(listModel: PlayListMediaFragmentModel.showPlayLists){

        binding!!.mediaEmptyText.isVisible = false
        binding!!.imageEmpty.isVisible = false
        binding!!.rv.isVisible = true
        binding!!.progressBarLayout.isVisible = false

        adapterInit(listModel.listPlaylists)

    }

    companion object {

        fun newInstance() = PlayListsMediaFragment()

    }
}