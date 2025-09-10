package kndroidx.wear.tile.widget

import androidx.wear.protolayout.LayoutElementBuilders.Image
import kndroidx.wear.tile.ModifierWrapper
import kndroidx.wear.tile.addLayoutElement

fun Any.Image(
    resId: String,
    modifier: ModifierWrapper? = null,
    block: (Image.Builder.() -> Unit)? = null,
) = Image.Builder().apply {
    if (resId.isNotEmpty()) setResourceId(resId)
    modifier?.let {
        setModifiers(it.build())
        it.layout(width = {}, height = {
            setWidth()
        })
    }
    block?.invoke(this)
}.build().apply {
    addLayoutElement(this@Image, this)
}
