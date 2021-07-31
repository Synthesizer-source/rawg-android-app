package com.synthesizer.source.rawg.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.ui.home.GamesAdapter.GamesViewHolder

class GamesAdapter : ListAdapter<Any, GamesViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_games, parent, false)
        return GamesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) { }

    override fun getItemCount(): Int {
        return 20
    }

    class GamesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    object diff : DiffUtil.ItemCallback<Any>() {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
}