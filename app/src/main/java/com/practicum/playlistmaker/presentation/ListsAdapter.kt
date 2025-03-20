package com.practicum.playlistmaker.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.databinding.SamplePlaylistBigBinding
import com.practicum.playlistmaker.databinding.SamplePlaylistSmallBinding
import com.practicum.playlistmaker.databinding.SampleUserSelectBinding
import com.practicum.playlistmaker.domain.model.PlayList

class ListsAdapter(private var listsList: List<PlayList>): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var typeH: Int = 0
    var callBack: ((idList: Long, name: String)->Unit)? = null
    var callBackBigList: ((playList: PlayList)->Unit)? = null
    var callBackUserSelect: ((ev: Int)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInspector = LayoutInflater.from(parent.context)

        if (typeH == 0) {
            return ListViewHolder(SamplePlaylistBigBinding.inflate(layoutInspector, parent, false))
        }else if(typeH == 2){
            if(viewType == 1) {
                return UserSelectViewHolder(
                    SampleUserSelectBinding.inflate(
                        layoutInspector,
                        parent,
                        false
                    )
                )
            }else{

                return ListViewHolderSmall(
                    SamplePlaylistSmallBinding.inflate(
                        layoutInspector,
                        parent,
                        false
                    )
                )
            }
        }
        else {
            return ListViewHolderSmall(
                SamplePlaylistSmallBinding.inflate(
                    layoutInspector,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount() = listsList.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ListViewHolder -> holder.bind(listsList[position])
            is ListViewHolderSmall -> holder.bind(listsList[position])
            is UserSelectViewHolder -> holder.bind(listsList[position])
        }

        holder.itemView.setOnClickListener{

            if (typeH == 1 && callBack != null){
                listsList[position].id?.let { it1 -> callBack!!.invoke(it1, listsList[position].name) }
            }

            if (typeH == 0 && callBackBigList != null){
                callBackBigList!!.invoke(listsList[position])
            }

            if (typeH == 2 && callBackUserSelect != null){
                callBackUserSelect!!.invoke(position)
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)

        return if (listsList[position].userAction){
            1
        }else{
            0
        }

    }

    fun updateLists(newPlayList: List<PlayList>) {
        listsList = newPlayList
    }

}