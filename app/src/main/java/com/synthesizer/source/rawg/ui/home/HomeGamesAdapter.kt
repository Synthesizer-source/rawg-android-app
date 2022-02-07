package com.synthesizer.source.rawg.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.rawg.databinding.ItemHomeGameListBinding
import com.synthesizer.source.rawg.domain.model.GameImage
import com.synthesizer.source.rawg.ui.home.HomeGamesAdapter.HomeScreenItemViewHolder
import com.synthesizer.source.rawg.utils.loadImage

class HomeGamesAdapter : RecyclerView.Adapter<HomeScreenItemViewHolder>() {

    private var _items = mutableListOf<GameImage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeGameListBinding.inflate(inflater, parent, false)
        return HomeScreenItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeScreenItemViewHolder, position: Int) {
        holder.bind(_items[position])
    }

    override fun getItemCount(): Int {
        return _items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(data: GameImage) {
        _items.add(data)
        notifyDataSetChanged()
    }

    fun getItem(position: Int) = _items[position]

    class HomeScreenItemViewHolder(private val binding: ItemHomeGameListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GameImage) {
            binding.homeScreenGameImage.loadImage(item.imageUrl)
        }
    }
}