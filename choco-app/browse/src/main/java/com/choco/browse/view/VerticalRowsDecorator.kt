package com.choco.browse.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalRowsDecorator(private val verticalOffset: Int, private val horizontalOffset: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = horizontalOffset
        outRect.right = horizontalOffset
        outRect.top = verticalOffset
        outRect.bottom = verticalOffset
    }
}