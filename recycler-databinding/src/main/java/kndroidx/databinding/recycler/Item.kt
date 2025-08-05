package kndroidx.databinding.recycler

import androidx.annotation.LayoutRes

sealed interface Item {
    @get:LayoutRes
    val layout: Int

    data class Data<T>(val data: T, @LayoutRes override val layout: Int) : Item
    data class Layout(@LayoutRes override val layout: Int) : Item
}