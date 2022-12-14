package com.synthesizer.source.rawg.ui.gamedetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.rawg.databinding.ItemGameDetailDescriptionComponentBinding
import com.synthesizer.source.rawg.databinding.ItemGameDetailHeaderComponentBinding
import com.synthesizer.source.rawg.databinding.ItemGameDetailScreenshotComponentBinding
import com.synthesizer.source.rawg.databinding.ItemGameDetailSummaryComponentBinding
import com.synthesizer.source.rawg.ui.gamedetail.component.Component
import com.synthesizer.source.rawg.ui.gamedetail.component.ViewType
import com.synthesizer.source.rawg.ui.gamedetail.component.description.DescriptionUIModel
import com.synthesizer.source.rawg.ui.gamedetail.component.header.HeaderUIModel
import com.synthesizer.source.rawg.ui.gamedetail.component.screenshot.ScreenshotUIModel
import com.synthesizer.source.rawg.ui.gamedetail.component.summary.SummaryUIModel
import com.synthesizer.source.rawg.ui.gamedetail.viewholder.DescriptionViewHolder
import com.synthesizer.source.rawg.ui.gamedetail.viewholder.HeaderViewHolder
import com.synthesizer.source.rawg.ui.gamedetail.viewholder.ScreenshotViewHolder
import com.synthesizer.source.rawg.ui.gamedetail.viewholder.SummaryViewHolder

class GameDetailAdapter : ListAdapter<Component, RecyclerView.ViewHolder>(DIFF) {

    object DIFF : DiffUtil.ItemCallback<Component>() {
        override fun areItemsTheSame(oldItem: Component, newItem: Component): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: Component, newItem: Component): Boolean {
            return oldItem.type == newItem.type
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ViewType.HEADER_VIEW_TYPE.type -> {
                val binding =
                    ItemGameDetailHeaderComponentBinding.inflate(inflater, parent, false)
                return HeaderViewHolder(binding)
            }

            ViewType.SUMMARY_VIEW_TYPE.type -> {
                val binding =
                    ItemGameDetailSummaryComponentBinding.inflate(inflater, parent, false)
                return SummaryViewHolder(binding)
            }

            ViewType.DESCRIPTION_VIEW_TYPE.type -> {
                val binding =
                    ItemGameDetailDescriptionComponentBinding.inflate(inflater, parent, false)
                return DescriptionViewHolder(binding)
            }

            else -> {
                val binding =
                    ItemGameDetailScreenshotComponentBinding.inflate(inflater, parent, false)
                return ScreenshotViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is HeaderUIModel -> (holder as HeaderViewHolder).bind(item)
            is SummaryUIModel -> (holder as SummaryViewHolder).bind(item)
            is DescriptionUIModel -> (holder as DescriptionViewHolder).bind(item)
            is ScreenshotUIModel -> (holder as ScreenshotViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            ViewType.HEADER_VIEW_TYPE.position -> ViewType.HEADER_VIEW_TYPE.type
            ViewType.SUMMARY_VIEW_TYPE.position -> ViewType.SUMMARY_VIEW_TYPE.type
            ViewType.DESCRIPTION_VIEW_TYPE.position -> ViewType.DESCRIPTION_VIEW_TYPE.type
            ViewType.SCREENSHOT_VIEW_TYPE.position -> ViewType.SCREENSHOT_VIEW_TYPE.type
            else -> -1
        }
    }
}