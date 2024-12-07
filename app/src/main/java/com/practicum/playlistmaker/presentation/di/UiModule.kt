package com.practicum.playlistmaker.presentation.di

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.presentation.TrackAdapter
import com.practicum.playlistmaker.presentation.UI.Media.EmptyMediaFragment
import com.practicum.playlistmaker.presentation.UI.Media.PagerAdapter
import com.practicum.playlistmaker.presentation.UI.Media.ParrentMediaFragment
import com.practicum.playlistmaker.presentation.ViewModels.MainViewModel
import com.practicum.playlistmaker.presentation.ViewModels.Media.EmptyMediaFragmentViewModel
import com.practicum.playlistmaker.presentation.ViewModels.Media.MediaViewModel
import com.practicum.playlistmaker.presentation.ViewModels.Media.ParrentMediaFragmentViewModel
import com.practicum.playlistmaker.presentation.ViewModels.ParentViewModel
import com.practicum.playlistmaker.presentation.ViewModels.PlayerViewModel
import com.practicum.playlistmaker.presentation.ViewModels.SearchFragmentViewModel
import com.practicum.playlistmaker.presentation.ViewModels.SettingsFragmentViewModel
import com.practicum.playlistmaker.presentation.ViewModels.SettingsViewModel
import org.koin.dsl.module

val uiModule = module {

    factory<MainViewModel> {
        MainViewModel(get())
    }

    factory<MediaViewModel> {
        MediaViewModel()
    }

    factory<PlayerViewModel> {(jsonTrack: String) ->
        PlayerViewModel(get(), get(), jsonTrack)
    }

    factory<SettingsViewModel> {
        SettingsViewModel(get(), get())
    }

    factory<EmptyMediaFragmentViewModel> {
        EmptyMediaFragmentViewModel()
    }

    factory<ParrentMediaFragmentViewModel> {
        ParrentMediaFragmentViewModel()
    }

    factory<TrackAdapter>{ (tracks: List<Track>) ->
        TrackAdapter(tracks,get(),get())
    }

    factory<PagerAdapter> {(hostFragment: Fragment) ->
        PagerAdapter(hostFragment)
    }

    factory<TabLayoutMediator> {(tabl: TabLayout, vp2: ViewPager2, myFunc: (TabLayout.Tab, Int) -> Unit) ->
        TabLayoutMediator(tabl, vp2, myFunc)
    }

    factory<EmptyMediaFragment> { (pos: Int) ->
        EmptyMediaFragment.newInstance(pos)
    }

    factory<ParrentMediaFragment> {
        ParrentMediaFragment.newInstance()
    }

    factory<ParentViewModel> {
        ParentViewModel()
    }
    factory<SettingsFragmentViewModel> {
        SettingsFragmentViewModel(get(), get())
    }

    factory<SearchFragmentViewModel> {
        SearchFragmentViewModel(get(), get())
    }

}