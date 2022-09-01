package com.synthesizer.source.rawg.ui.gamedetail.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class ComponentItemDecoration(
    @DimenRes private val verticalSpacing: Int,
    @DimenRes private val horizontalSpace: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val vSpace = view.context.resources.getDimensionPixelSize(verticalSpacing)
        val hSpace = view.context.resources.getDimensionPixelSize(horizontalSpace)

        val position = parent.getChildAdapterPosition(view)

        if (position != 0) {
            outRect.top = vSpace / 2
            outRect.bottom = vSpace / 2
            outRect.right = hSpace
            outRect.left = hSpace
        }
    }
}