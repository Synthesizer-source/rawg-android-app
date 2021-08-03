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
                gameBackground.loadImage(item.background_image)
                metacriticProgress.progress = item.metacritic
                val color: Int = when (item.metacritic) {
                    in 0..55 -> R.color.darker_red
                    in 56..75 -> R.color.darker_yellow
                    else -> R.color.darker_green
                }

                metacriticPoint.text = item.metacritic.toString()
                metacriticPoint.setTextColor(ContextCompat.getColor(itemView.context, color))
                metacriticProgress.progressTintList =
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