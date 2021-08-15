package com.synthesizer.source.rawg.ui.gamedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.rawg.data.remote.ShortScreenshotResponse
import com.synthesizer.source.rawg.databinding.ItemGameDetailScreenshotBinding
import com.synthesizer.source.rawg.ui.gamedetail.GameDetailScreenshotAdapter.GameDetailScreenshotViewHolder
import com.synthesizer.source.rawg.utils.loadImage

class GameDetailScreenshotAdapter :
    ListAdapter<ShortScreenshotResponse, GameDetailScreenshotViewHolder>(diff) {


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

        fun bind(item: ShortScreenshotResponse) {
            binding.screenshot.loadImage(item.image)
        }
    }

    object diff : DiffUtil.ItemCallback<ShortScreenshotResponse>() {
        override fun areItemsTheSame(oldItem: ShortScreenshotResponse, newItem: ShortScreenshotResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShortScreenshotResponse,
            newItem: ShortScreenshotResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}