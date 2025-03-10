package com.practicum.playlistmaker.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.databinding.SamplePlaylistBigBinding
import com.practicum.playlistmaker.databinding.SamplePlaylistSmallBinding
import com.practicum.playlistmaker.domain.model.PlayList

class ListsAdapter(private var listsList: List<PlayList>): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var typeH: Int = 0
    var callBack: ((idList: Long, name: String)->Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInspector = LayoutInflater.from(parent.context)

        if (typeH == 0)
            return ListViewHolder(SamplePlaylistBigBinding.inflate(layoutInspector, parent, false))
        else
            return ListViewHolderSmall(SamplePlaylistSmallBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount() = listsList.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (typeH == 0){
            (holder as ListViewHolder).bind(listsList[position])
        }else{
            (holder as ListViewHolderSmall).bind(listsList[position])
        }

        holder.itemView.setOnClickListener{

            if (typeH == 1 && callBack != null){
                listsList[position].id?.let { it1 -> callBack!!.invoke(it1, listsList[position].name) }
            }

        }

    }

    fun updateLists(newPlayList: List<PlayList>) {
        listsList = newPlayList
    }

}