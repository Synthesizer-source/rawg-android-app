package com.synthesizer.source.rawg.ui.gamedetail.component.screenshot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.rawg.databinding.ItemGameDetailScreenshotBinding
import com.synthesizer.source.rawg.domain.model.GameImage
import com.synthesizer.source.rawg.ui.gamedetail.component.screenshot.ScreenshotAdapter.GameDetailScreenshotViewHolder
import com.synthesizer.source.rawg.utils.loadImage

class ScreenshotAdapter :
    ListAdapter<GameImage, GameDetailScreenshotViewHolder>(DIFF) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameDetailScreenshotViewHolder {
        val binding = ItemGameDetailScreenshotBinding.inflate(LayoutInflater.from(parent.context))
        return GameDetailScreenshotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameDetailScreenshotViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class GameDetailScreenshotViewHolder(private val binding: ItemGameDetailScreenshotBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GameImage) {
            binding.screenshot.loadImage(item.imageUrl)
        }
    }

    private object DIFF : DiffUtil.ItemCallback<GameImage>() {
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
            return oldItem.id == newItem.id
        }
    }
}