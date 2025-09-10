package kndroidx.wear.tile.layout

import androidx.wear.protolayout.LayoutElementBuilders
import kndroidx.wear.tile.addLayoutElement
import androidx.wear.protolayout.ModifiersBuilders.Modifiers.Builder as ModifiersBuilder

fun Any.Arc(
    modifier: ModifiersBuilder? = null,
    block: (LayoutElementBuilders.Arc.Builder.() -> Unit)? = null,
) = LayoutElementBuilders.Arc.Builder().apply {
    modifier?.let { setModifiers(it.build()) }
    block?.invoke(this)
}.build().apply {
    addLayoutElement(this, this)
}