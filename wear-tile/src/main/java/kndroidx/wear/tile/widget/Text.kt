package kndroidx.wear.tile.widget

import androidx.wear.protolayout.LayoutElementBuilders.FontStyle
import androidx.wear.protolayout.material.Text
import kndroidx.wear.tile.ModifierWrapper
import kndroidx.wear.tile.addLayoutElement

fun Any.Text(
    text: String,
    fontStyle: FontStyle? = null,
    typography: Int? = null,
    isScalable: Boolean? = null,
    modifier: ModifierWrapper? = null,
    multilineAlignment: Int? = null,
    maxLines: Int? = null,
    overflow: Int? = null,
    block: (Text.Builder.() -> Unit)? = null,
) =
    Text.Builder(kndroidx.KndroidX.context, text).apply {
        fontStyle?.let { style ->
            // only support four attr
            style.color?.let { setColor(it) }
            style.italic?.value?.let { setItalic(it) }
            style.weight?.value?.let { setWeight(it) }
            style.underline?.value?.let { setUnderline(it) }
        }
        overflow?.let { setOverflow(it) }
        multilineAlignment?.let { setMultilineAlignment(it) }
        isScalable?.let { setScalable(it) }
        maxLines?.let { setMaxLines(it) }
        typography?.let { setTypography(it) }
        modifier?.let { setModifiers(it.build()) }
        block?.invoke(this)
    }.build().apply {
        addLayoutElement(this@Text, this)
    }
