package com.synthesizer.source.rawg.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.data.remote.Result
import com.synthesizer.source.rawg.ui.home.GamesAdapter.GamesViewHolder
import com.synthesizer.source.rawg.utils.loadImage

class GamesAdapter : ListAdapter<Result, GamesViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_games, parent, false)
        return GamesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class GamesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Result) {
            itemView.findViewById<TextView>(R.id.name).setText(item.name)
            itemView.findViewById<ImageView>(R.id.gameBackground).loadImage(item.background_image)
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