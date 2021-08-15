package com.synthesizer.source.rawg.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.rawg.databinding.ItemHomeScreenGameBinding
import com.synthesizer.source.rawg.data.domain.HomeGameItem
import com.synthesizer.source.rawg.ui.home.HomeGamesAdapter.HomeScreenItemViewHolder
import com.synthesizer.source.rawg.utils.loadImage

class HomeGamesAdapter : RecyclerView.Adapter<HomeScreenItemViewHolder>() {

    private var _items = listOf<HomeGameItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeScreenGameBinding.inflate(inflater, parent, false)
        return HomeScreenItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeScreenItemViewHolder, position: Int) {
        holder.bind(_items[position])
    }

    override fun getItemCount(): Int {
        return _items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadDataSet(items: List<HomeGameItem>) {
        _items = items
        notifyItemChanged(_items.size - 1)
    }

    fun getItem(position: Int) = _items[position]

    class HomeScreenItemViewHolder(private val binding: ItemHomeScreenGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeGameItem) {
            binding.homeScreenGameImage.loadImage(item.imageUrl)
        }
    }
}