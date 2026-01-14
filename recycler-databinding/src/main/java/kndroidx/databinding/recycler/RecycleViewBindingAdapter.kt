package kndroidx.databinding.recycler

import android.app.Activity
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.LayoutParams

@Suppress("UNCHECKED_CAST")
@BindingAdapter(
    value = [
        "item",
        "itemLayout",
        "itemClick",
        "itemLongClick",
        "itemViewType",
        "itemEventHandler",
        "itemHelper",
        "viewModel",
        "activity",
        "fragment"
    ],
    requireAll = false
)
fun RecyclerView.setData(
    item: List<Any>?,
    @LayoutRes itemLayout: Int?,
    listener: BaseBindingAdapter.OnItemClickListener<Any>?,
    longListener: BaseBindingAdapter.OnItemClickListener<Any>?,
    itemViewTypeCreator: BaseBindingAdapter.ItemViewTypeCreator?,
    itemEventHandler: Any?,
    itemHelper: Any?,
    viewModel: Any?,
    activity: Any?,
    fragment: Any?
) {
    if (adapter == null) {
        if (itemLayout == null) {
            if (item == null) return
            runCatching {
                item as List<Item>
                adapter = ItemBindingAdapter().also {
                    it.item = item
                    it.itemLongClickListener = longListener
                    it.itemClickListener = listener
                    it.itemEventHandler = itemEventHandler
                    it.viewModel = viewModel
                    it.activity = activity
                    it.fragment = fragment
                    it.itemHelper = itemHelper
                }
            }
        } else {
            adapter = DefaultBindingAdapter(itemLayout).also {
                it.item = item
                it.itemLongClickListener = longListener
                it.itemClickListener = listener
                it.itemViewTypeCreator = itemViewTypeCreator
                it.itemEventHandler = itemEventHandler
                it.viewModel = viewModel
                it.activity = activity
                it.fragment = fragment
                it.itemHelper = itemHelper
            }
        }
    } else if (adapter is BaseBindingAdapter<*, *>) {
        (adapter as BaseBindingAdapter<Any, ViewDataBinding>).let {
            it.item = item
            it.itemViewTypeCreator = itemViewTypeCreator
            it.itemClickListener = listener
            it.viewModel = viewModel
            it.itemLongClickListener = longListener
            it.fragment = fragment
            it.activity = activity
            it.itemEventHandler = itemEventHandler
            it.itemHelper = itemHelper
        }
    } else if (adapter is ItemBindingAdapter) {
        if (item == null) return
        runCatching {
            item as List<Item>
            (adapter as ItemBindingAdapter).item = item
        }
    }
}

private fun handItemDecoration(
    recyclerView: RecyclerView, itemDecorations: List<ItemDecoration>?
) {
    if (itemDecorations == null) {
        return
    }
    val oldItemDecorations = arrayListOf<ItemDecoration>()
    for (i in 0 until recyclerView.itemDecorationCount) {
        oldItemDecorations.add(recyclerView.getItemDecorationAt(i))
    }
    oldItemDecorations.forEach {
        recyclerView.removeItemDecoration(it)
    }
    itemDecorations.forEach {
        recyclerView.addItemDecoration(it)
    }
}


@BindingAdapter(
    value = ["dividerOrientation", "dividerSize", "dividerColor", "dividerLeftPadding", "dividerRightPadding", "dividerTopPadding", "dividerBottomPadding", "itemDecorations"],
    requireAll = false
)
fun setDivider(
    recyclerView: RecyclerView,
    dividerOrientation: Int?,
    dividerSize: Float?,
    dividerColor: Int?,
    dividerLeftPadding: Float?,
    dividerRightPadding: Float?,
    dividerTopPadding: Float?,
    dividerBottomPadding: Float?,
    itemDecorations: List<ItemDecoration>?
) {
    if (dividerOrientation != null || dividerSize != null || dividerColor != null || dividerLeftPadding != null || dividerRightPadding != null || dividerTopPadding != null || dividerBottomPadding != null) {
        val orientation = dividerOrientation ?: DividerItemDecoration.VERTICAL
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, orientation)
        var insetDrawable =
            recyclerView.context.resources.getDrawable(R.drawable.recyclerview_divider, null)
                .mutate()
        if (insetDrawable is InsetDrawable) {
            val shapeDrawable = insetDrawable.drawable
            if (shapeDrawable is GradientDrawable) {
                shapeDrawable.setColor(
                    dividerColor
                        ?: recyclerView.context.resources.getColor(R.color.ardf_recyclerview_divider_color)
                )
                val width =
                    if (orientation == DividerItemDecoration.VERTICAL) LayoutParams.MATCH_PARENT else dividerSize
                        ?: recyclerView.context.resources.getDimensionPixelSize(R.dimen.ardf_recyclerview_divider_size)
                val height =
                    if (orientation == DividerItemDecoration.HORIZONTAL) LayoutParams.MATCH_PARENT else dividerSize
                        ?: recyclerView.context.resources.getDimensionPixelSize(R.dimen.ardf_recyclerview_divider_size)
                shapeDrawable.setSize(width.toInt(), height.toInt())
            }
            insetDrawable = InsetDrawable(
                shapeDrawable,
                dividerLeftPadding?.toInt() ?: 0,
                dividerTopPadding?.toInt() ?: 0,
                dividerRightPadding?.toInt() ?: 0,
                dividerBottomPadding?.toInt() ?: 0
            )
        }
        dividerItemDecoration.setDrawable(insetDrawable)
        if (itemDecorations == null) {
            recyclerView.addItemDecoration(dividerItemDecoration)
        } else {
            val list = itemDecorations.toMutableList()
            list.add(dividerItemDecoration)
            handItemDecoration(recyclerView, list)
        }
    } else {
        handItemDecoration(recyclerView, itemDecorations)
    }
}
