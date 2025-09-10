package kndroidx.wear.tile.layout

import androidx.wear.protolayout.LayoutElementBuilders
import kndroidx.wear.tile.ModifierWrapper
import kndroidx.wear.tile.addLayoutElement

fun Any.Arc(
    modifier: ModifierWrapper? = null,
    block: (LayoutElementBuilders.Arc.Builder.() -> Unit)? = null,
) = LayoutElementBuilders.Arc.Builder().apply {
    modifier?.let { setModifiers(it.build()) }
    block?.invoke(this)
}.build().apply {
    addLayoutElement(this, this)
}
