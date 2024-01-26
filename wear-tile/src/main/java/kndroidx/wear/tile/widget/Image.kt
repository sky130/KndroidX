package kndroidx.wear.tile.widget

import androidx.wear.protolayout.DimensionBuilders.DpProp
import androidx.wear.protolayout.LayoutElementBuilders.Image
import kndroidx.wear.tile.addLayoutElement
import androidx.wear.protolayout.ModifiersBuilders.Modifiers.Builder as ModifiersBuilder

fun Any.Image(
    width: DpProp,
    height: DpProp,
    resId: String,
    modifier: ModifiersBuilder? = null,
    block: (Image.Builder.() -> Unit)? = null,
) = Image.Builder().apply {
    if (resId.isNotEmpty()) setResourceId(resId)
    setWidth(width)
    setHeight(height)
    modifier?.let { setModifiers(it.build()) }
    block?.invoke(this)
}.build().apply {
    addLayoutElement(this@Image, this)
}
