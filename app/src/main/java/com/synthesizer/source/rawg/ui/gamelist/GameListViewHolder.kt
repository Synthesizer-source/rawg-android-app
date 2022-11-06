package com.synthesizer.source.rawg.ui.gamelist

import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.rawg.databinding.ItemGameListBinding
import com.synthesizer.source.rawg.domain.model.GameListItem

class GameListViewHolder(
    val itemBinding: ItemGameListBinding,
    private val itemClickListener: (Int) -> Unit
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: GameListItem) {
        itemBinding.root.apply {
            initialize(item)
            setOnClickListener {
                itemClickListener(item.id)
            }
        }
    }
}