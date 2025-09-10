package kndroidx.wear.tile.widget

import androidx.wear.protolayout.LayoutElementBuilders.ArcLine
import kndroidx.wear.tile.ArcModifierWrapper
import kndroidx.wear.tile.addLayoutElement

fun Any.ArcLine(modifier: ArcModifierWrapper? = null,) = ArcLine.Builder().apply {
    modifier?.let {
        setModifiers(it.build())
        it.thickness?.let { i -> setThickness(i) }
        it.color?.let { i -> setColor(i) }
        it.brush?.let { it1 -> setBrush(it1) }
        it.arcDirection?.let { it1 -> setArcDirection(it1) }
    }
//    setLayoutConstraintsForDynamicLength()
}.build().apply {
    addLayoutElement(this@ArcLine, this)
}