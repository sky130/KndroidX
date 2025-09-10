package kndroidx.wear.tile.layout

import androidx.wear.protolayout.LayoutElementBuilders
import kndroidx.wear.tile.ModifierWrapper
import kndroidx.wear.tile.addLayoutElement

fun Any.Column(
    modifier: ModifierWrapper? = null,
    horizontalAlignment: Int? = null,
    block: (LayoutElementBuilders.Column.Builder.() -> Unit)? = null,
) = LayoutElementBuilders.Column.Builder().apply {
    horizontalAlignment?.let { setHorizontalAlignment(it) }
    modifier?.let {
        it.layout(::setWidth, ::setHeight)
        setModifiers(it.build())
    }
    block?.invoke(this)
}.build().apply {
    addLayoutElement(this@Column, this)
}