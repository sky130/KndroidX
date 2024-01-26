package kndroidx.wear.tile.widget

import androidx.wear.protolayout.ColorBuilders
import androidx.wear.protolayout.material.Text
import kndroidx.wear.tile.addLayoutElement
import androidx.wear.protolayout.ModifiersBuilders.Modifiers.Builder as ModifiersBuilder

fun Any.Text(
    text: String,
    textColors: ColorBuilders.ColorProp? = null,
    typography: Int? = null,
    modifier: ModifiersBuilder? = null,
    block: (Text.Builder.() -> Unit)? = null,
) =
    Text.Builder(kndroidx.KndroidX.context, text).apply {
        textColors?.let { setColor(it) }
        typography?.let { setTypography(it) }
        modifier?.let { setModifiers(it.build()) }
        block?.invoke(this)
    }.build().apply {
        addLayoutElement(this@Text, this)
    }
