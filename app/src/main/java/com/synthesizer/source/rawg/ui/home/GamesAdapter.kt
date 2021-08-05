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
            itemBinding.apply {
                name.text = domainItem.name
                background.loadImage(domainItem.imageUrl)
                domainItem.platforms.forEach { showPlatform(it) }

                root.setOnClickListener {
                    itemClickListener.invoke(item.id)
                }
            }
        }

        private fun showPlatform(platform: String) {
            itemBinding.apply {
                when (platform) {
                    "pc" -> pcPlatformIcon.setVisibility(true)
                    "playstation" -> psPlatformIcon.setVisibility(true)
                    "xbox" -> xboxPlatformIcon.setVisibility(true)
                    "nintendo" -> nintendoPlatformIcon.setVisibility(true)
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