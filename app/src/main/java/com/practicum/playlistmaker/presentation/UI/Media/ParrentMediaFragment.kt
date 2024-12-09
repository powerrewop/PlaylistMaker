package com.practicum.playlistmaker.presentation.UI.Media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ParrentMediaFragmentBinding
import com.practicum.playlistmaker.presentation.ViewModels.Media.ParrentMediaFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin

class ParrentMediaFragment : Fragment() {

    private var binding: ParrentMediaFragmentBinding? = null
    private lateinit var tabMediator: TabLayoutMediator
    private val viewModel: ParrentMediaFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ParrentMediaFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter: PagerAdapter = getKoin().get(PagerAdapter::class,null ,{parametersOf(this)})
        binding!!.pager.adapter = adapter

        tabMediator = getKoin().get(TabLayoutMediator::class, null) {
            parametersOf(
                binding!!.tabLayout,
                binding!!.pager,
                { tab: TabLayout.Tab, position: Int ->
                    when (position) {
                        0 -> tab.text = getString(R.string.text_media_favorites)
                        1 -> tab.text = getString(R.string.text_paylists)
                    }
                })
        }

        tabMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            tabMediator.detach()
        } catch (e: Throwable) {

        }
    }

    companion object {
        fun newInstance() = ParrentMediaFragment()
    }

}