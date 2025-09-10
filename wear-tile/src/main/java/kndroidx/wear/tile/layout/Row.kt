package kndroidx.wear.tile.layout

import androidx.wear.protolayout.LayoutElementBuilders
import kndroidx.wear.tile.ModifierWrapper
import kndroidx.wear.tile.addLayoutElement

fun Any.Row(
    modifier: ModifierWrapper? = null,
    verticalAlignment : Int? = null,
    block: (LayoutElementBuilders.Row.Builder.() -> Unit)? = null,
) = LayoutElementBuilders.Row.Builder().apply {
    verticalAlignment?.let { setVerticalAlignment(it) }
    modifier?.let {
        it.layout(::setWidth, ::setHeight)
        setModifiers(it.build())
    }
    block?.invoke(this)
}.build().apply {
    addLayoutElement(this@Row, this)
}