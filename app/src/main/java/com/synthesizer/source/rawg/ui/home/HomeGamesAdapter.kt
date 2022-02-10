package com.synthesizer.source.rawg.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.rawg.databinding.ItemHomeGameListBinding
import com.synthesizer.source.rawg.domain.model.GameImage
import com.synthesizer.source.rawg.utils.gone
import com.synthesizer.source.rawg.utils.invisible
import com.synthesizer.source.rawg.utils.loadImageWithCallback
import com.synthesizer.source.rawg.utils.visible

class HomeGamesAdapter :
    ListAdapter<GameImage, HomeGamesAdapter.ViewHolder>(DIFF) {

    var imageLoadedListener: () -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeGameListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: ItemHomeGameListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GameImage) {
            if (item.imageUrl.isEmpty()) {
                binding.shimmerBackground.visible()
                binding.homeScreenGameImage.invisible()
            } else {
                binding.homeScreenGameImage.loadImageWithCallback(item.imageUrl, success = {
                    binding.shimmerBackground.gone()
                    binding.homeScreenGameImage.visible()
                    imageLoadedListener()
                })
            }
        }
    }

    object DIFF : DiffUtil.ItemCallback<GameImage>() {
        override fun areItemsTheSame(
            oldItem: GameImage,
            newItem: GameImage
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GameImage,
            newItem: GameImage
        ): Boolean {
            return oldItem.toString() == newItem.toString()
        }

    }
}