package kndroidx.databinding.recycler

import androidx.annotation.LayoutRes

sealed interface Item {
    @get:LayoutRes
    val layout: Int

    data class Data<T>(
        val data: T,
        @param:LayoutRes override val layout: Int,
        val isClickable: Boolean = true
    ) : Item {
        override operator fun equals(other: Any?): Boolean {
            return if (other is Data<*>) other.data == this.data
            else other == this.data
        }

        override fun hashCode(): Int {
            var result = layout
            result = 31 * result + isClickable.hashCode()
            result = 31 * result + (data?.hashCode() ?: 0)
            return result
        }
    }

    data class Layout(@param:LayoutRes override val layout: Int) : Item
}