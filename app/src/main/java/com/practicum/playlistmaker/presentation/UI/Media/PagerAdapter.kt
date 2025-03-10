package com.practicum.playlistmaker.presentation.UI.Media

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin

class PagerAdapter(hostFragment: Fragment) : FragmentStateAdapter(hostFragment) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {

        if(position == 0){
            return getKoin().get(FavoritesMediaFragment::class)
        }

        //return getKoin().get(PlayListsMediaFragment::class,null ,{ parametersOf(position) })
        return getKoin().get(PlayListsMediaFragment::class)

    }
}