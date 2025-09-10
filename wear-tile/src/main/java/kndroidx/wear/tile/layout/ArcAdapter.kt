package kndroidx.wear.tile.layout

import androidx.wear.protolayout.LayoutElementBuilders
import kndroidx.wear.tile.Tile
import kndroidx.wear.tile.addLayoutElement

fun Any.ArcAdapter(
    isRotateContents: Boolean? = null,
    content: Tile.() -> LayoutElementBuilders.LayoutElement
) = LayoutElementBuilders.ArcAdapter.Builder().apply {
    setContent(Tile.content())
    isRotateContents?.let { setRotateContents(it) }
}.build().apply {
    addLayoutElement(this@ArcAdapter, this)
}