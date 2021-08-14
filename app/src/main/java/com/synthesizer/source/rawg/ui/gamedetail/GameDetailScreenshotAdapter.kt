package com.synthesizer.source.rawg.ui.gamedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.rawg.data.remote.ShortScreenshot
import com.synthesizer.source.rawg.databinding.ItemGameDetailScreenshotBinding
import com.synthesizer.source.rawg.ui.gamedetail.GameDetailScreenshotAdapter.GameDetailScreenshotViewHolder
import com.synthesizer.source.rawg.utils.loadImage

class GameDetailScreenshotAdapter :
    ListAdapter<ShortScreenshot, GameDetailScreenshotViewHolder>(diff) {


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

        fun bind(item: ShortScreenshot) {
            binding.screenshot.loadImage(item.image)
        }
    }

    object diff : DiffUtil.ItemCallback<ShortScreenshot>() {
        override fun areItemsTheSame(oldItem: ShortScreenshot, newItem: ShortScreenshot): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShortScreenshot,
            newItem: ShortScreenshot
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}