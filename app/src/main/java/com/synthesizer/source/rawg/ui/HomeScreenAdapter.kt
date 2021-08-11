package com.synthesizer.source.rawg.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synthesizer.source.rawg.databinding.ItemHomeScreenGameBinding
import com.synthesizer.source.rawg.ui.HomeScreenAdapter.HomeScreenItemViewHolder

class HomeScreenAdapter(private val items: List<HomeScreenItem>) :
    RecyclerView.Adapter<HomeScreenItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeScreenGameBinding.inflate(inflater, parent, false)
        return HomeScreenItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeScreenItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int) = items[position]

    class HomeScreenItemViewHolder(private val binding: ItemHomeScreenGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeScreenItem) {
            Glide.with(this.itemView.context)
                .load(item.resId)
                .override(512)
                .into(binding.homeScreenGameImage);
        }
    }
}