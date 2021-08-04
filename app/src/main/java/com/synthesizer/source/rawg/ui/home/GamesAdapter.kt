package com.synthesizer.source.rawg.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.data.remote.Result
import com.synthesizer.source.rawg.databinding.ItemGamesBinding
import com.synthesizer.source.rawg.ui.home.GamesAdapter.GamesViewHolder
import com.synthesizer.source.rawg.utils.loadImage

class GamesAdapter : PagingDataAdapter<Result, GamesViewHolder>(diff) {

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
        fun bind(item: Result) {
            itemBinding.apply {
                name.text = item.name
                background.loadImage(item.background_image)
                scoreProgress.progress = item.metacritic
                val color: Int = when (item.metacritic) {
                    in 0..55 -> R.color.red_dark
                    in 56..75 -> R.color.yellow_dark
                    else -> R.color.green_dark
                }

                metascore.text = item.metacritic.toString()
                metascore.setTextColor(ContextCompat.getColor(itemView.context, color))
                scoreProgress.progressTintList =
                    ContextCompat.getColorStateList(itemView.context, color)

                root.setOnClickListener {
                    itemClickListener.invoke(item.id)
                }
            }
        }
    }

    object diff : DiffUtil.ItemCallback<Result>() {

        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }
    }
}