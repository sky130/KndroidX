package kndroidx.wear.tile.widget

import androidx.wear.protolayout.DimensionBuilders.DpProp
import androidx.wear.protolayout.LayoutElementBuilders.Spacer
import kndroidx.wear.tile.addLayoutElement
import androidx.wear.protolayout.ModifiersBuilders.Modifiers.Builder as ModifiersBuilder

fun Any.Spacer(
    width: DpProp,
    height: DpProp,
    modifier: ModifiersBuilder? = null,
    block: (Spacer.Builder.() -> Unit)? = null,
) = Spacer.Builder().apply {
    setWidth(width)
    setHeight(height)
    modifier?.let { setModifiers(it.build()) }
    block?.invoke(this)
}.build().apply {
    addLayoutElement(this@Spacer, this)
}
