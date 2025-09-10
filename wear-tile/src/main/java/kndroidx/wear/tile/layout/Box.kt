package kndroidx.wear.tile.layout

import androidx.wear.protolayout.LayoutElementBuilders
import kndroidx.wear.tile.ModifierWrapper
import kndroidx.wear.tile.addLayoutElement

fun Any.Box(
    modifier: ModifierWrapper? = null,
    block: (LayoutElementBuilders.Box.Builder.() -> Unit)? = null,
) = LayoutElementBuilders.Box.Builder().apply {
    modifier?.let {
        it.layout(::setWidth, ::setHeight)
        setModifiers(it.build())
    }
    block?.invoke(this)
}.build().apply {
    addLayoutElement(this@Box, this)
}