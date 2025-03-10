package com.practicum.playlistmaker.presentation.di

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.playlistmaker.domain.model.PlayList
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.presentation.ListsAdapter
import com.practicum.playlistmaker.presentation.TrackAdapter
import com.practicum.playlistmaker.presentation.UI.CreateListFragment
import com.practicum.playlistmaker.presentation.UI.Media.PlayListsMediaFragment
import com.practicum.playlistmaker.presentation.UI.Media.FavoritesMediaFragment
import com.practicum.playlistmaker.presentation.UI.Media.PagerAdapter
import com.practicum.playlistmaker.presentation.UI.Media.ParrentMediaFragment
import com.practicum.playlistmaker.presentation.UI.PlayerFragment
import com.practicum.playlistmaker.presentation.ViewModels.CreateListFragmentViewModel
import com.practicum.playlistmaker.presentation.ViewModels.Media.PlayListsMediaFragmentViewModel
import com.practicum.playlistmaker.presentation.ViewModels.Media.FavoritesMediaFragmentViewModel
import com.practicum.playlistmaker.presentation.ViewModels.Media.ParrentMediaFragmentViewModel
import com.practicum.playlistmaker.presentation.ViewModels.ParentViewModel
import com.practicum.playlistmaker.presentation.ViewModels.PlayerFragmentViewModel
import com.practicum.playlistmaker.presentation.ViewModels.SearchFragmentViewModel
import com.practicum.playlistmaker.presentation.ViewModels.SettingsFragmentViewModel
import org.koin.dsl.module

val uiModule = module {

    factory<PlayListsMediaFragmentViewModel> {
        PlayListsMediaFragmentViewModel(get())
    }

    factory<ParrentMediaFragmentViewModel> {
        ParrentMediaFragmentViewModel()
    }

    factory<TrackAdapter>{ (tracks: List<Track>) ->
        TrackAdapter(tracks,get(),get())
    }

    factory<ListsAdapter>{ (lists: List<PlayList>) ->
        ListsAdapter(lists)
    }

    factory<PagerAdapter> {(hostFragment: Fragment) ->
        PagerAdapter(hostFragment)
    }

    factory<TabLayoutMediator> {(tabl: TabLayout, vp2: ViewPager2, myFunc: (TabLayout.Tab, Int) -> Unit) ->
        TabLayoutMediator(tabl, vp2, myFunc)
    }

//    factory<PlayListsMediaFragment> { (pos: Int) ->
//        PlayListsMediaFragment.newInstance(pos)
//    }

    factory<PlayListsMediaFragment> {
        PlayListsMediaFragment.newInstance()
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
        SearchFragmentViewModel(get(), get(), get())
    }

    factory<FavoritesMediaFragment> {
        FavoritesMediaFragment.newInstance()
    }

    factory<FavoritesMediaFragmentViewModel> {
        FavoritesMediaFragmentViewModel(get())
    }

    factory<CreateListFragmentViewModel> {
        CreateListFragmentViewModel(get())
    }

    factory<CreateListFragment> {
        CreateListFragment.newInstance()
    }


    factory<PlayerFragmentViewModel> {(jsonTrack: String) ->
        PlayerFragmentViewModel(get(), get(), jsonTrack, get(), get(), get())
    }

}