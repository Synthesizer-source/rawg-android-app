package com.synthesizer.source.rawg.ui.gamedetail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.rawg.databinding.ItemGameDetailScreenshotComponentBinding
import com.synthesizer.source.rawg.ui.gamedetail.component.screenshot.ScreenshotUIModel

class ScreenshotViewHolder(private val binding: ItemGameDetailScreenshotComponentBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ScreenshotUIModel) {
        binding.root.initialize(item)
    }
}