package kndroidx.wear.tile.widget

import androidx.wear.protolayout.LayoutElementBuilders.ArcText
import androidx.wear.protolayout.LayoutElementBuilders.FontStyle
import kndroidx.wear.tile.ArcModifierWrapper
import kndroidx.wear.tile.addLayoutElement

fun Any.ArcText(
    text: String,
    fontStyle: FontStyle? = null,
    modifier: ArcModifierWrapper? = null
) = ArcText.Builder().apply {
    setText(text)
    modifier?.let {
        fontStyle?.let { style -> setFontStyle(style) }
        setModifiers(it.build())
        it.arcDirection?.let { it1 -> setArcDirection(it1) }
    }
}.build().apply {
    addLayoutElement(this@ArcText, this)
}