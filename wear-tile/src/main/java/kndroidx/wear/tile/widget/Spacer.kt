package kndroidx.wear.tile.widget

import androidx.wear.protolayout.LayoutElementBuilders.Spacer
import kndroidx.wear.tile.ModifierWrapper
import kndroidx.wear.tile.addLayoutElement

fun Any.Spacer(
    modifier: ModifierWrapper? = null,
    block: (Spacer.Builder.() -> Unit)? = null,
) = Spacer.Builder().apply {
    modifier?.let {
        setModifiers(it.build())
        setWidth(it.width)
        setHeight(it.height)
    }
    modifier?.let { setModifiers(it.build()) }
    block?.invoke(this)
}.build().apply {
    addLayoutElement(this@Spacer, this)
}
