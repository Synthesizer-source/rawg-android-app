package com.synthesizer.source.rawg.ui.gamelist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.synthesizer.source.rawg.databinding.ItemGameListBinding
import com.synthesizer.source.rawg.domain.model.GameListItem

class GameListAdapter : PagingDataAdapter<GameListItem, GameListViewHolder>(DIFF) {

    var itemClickListener: (id: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListViewHolder {
        val itemView =
            ItemGameListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameListViewHolder(itemView, itemClickListener)
    }

    override fun onBindViewHolder(holder: GameListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onViewRecycled(holder: GameListViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(holder.itemBinding.root).clear(holder.itemBinding.root.backgroundImage)
    }

    private object DIFF : DiffUtil.ItemCallback<GameListItem>() {

        override fun areItemsTheSame(oldItem: GameListItem, newItem: GameListItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: GameListItem, newItem: GameListItem): Boolean {
            return oldItem.id == newItem.id
        }
    }
}