package com.synthesizer.source.rawg.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.rawg.data.mapper.toDomain
import com.synthesizer.source.rawg.data.remote.GameRemote
import com.synthesizer.source.rawg.databinding.ItemGamesBinding
import com.synthesizer.source.rawg.ui.home.GamesAdapter.GamesViewHolder
import com.synthesizer.source.rawg.utils.loadImage
import com.synthesizer.source.rawg.utils.setVisibility

class GamesAdapter : PagingDataAdapter<GameRemote, GamesViewHolder>(diff) {

    var itemClickListener: (id: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val itemView = ItemGamesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GamesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class GamesViewHolder(private val itemBinding: ItemGamesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: GameRemote) {
            val domainItem = item.toDomain()
            with(itemBinding) {

                name.text = domainItem.name
                background.loadImage(domainItem.imageUrl)

                val isPcGame = domainItem.platforms.any { it == "pc" }
                val isPsGame = domainItem.platforms.any { it == "playstation" }
                val isXboxGame = domainItem.platforms.any { it == "xbox" }
                val isNintendoGame = domainItem.platforms.any { it == "nintendo" }

                pcPlatformIcon.setVisibility(isPcGame)
                psPlatformIcon.setVisibility(isPsGame)
                xboxPlatformIcon.setVisibility(isXboxGame)
                nintendoPlatformIcon.setVisibility(isNintendoGame)

                root.setOnClickListener {
                    itemClickListener.invoke(item.id)
                }
            }
        }
    }

    object diff : DiffUtil.ItemCallback<GameRemote>() {

        override fun areItemsTheSame(oldItem: GameRemote, newItem: GameRemote): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: GameRemote, newItem: GameRemote): Boolean {
            return oldItem.id == newItem.id
        }
    }
}