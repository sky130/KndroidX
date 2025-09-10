package kndroidx.wear.tile

import androidx.wear.protolayout.LayoutElementBuilders.LayoutElement
import androidx.wear.tiles.TileService
import kndroidx.wear.tile.layout.Box
import androidx.wear.protolayout.LayoutElementBuilders.Box.Builder as BoxBuilder

fun TileService.layout(block: BoxBuilder.() -> Unit) =
    Box(modifier = Modifier.fillMaxSize(), block = block)

fun Any.element(block: () -> LayoutElement) {
    addLayoutElement(this, block())
}

object Tile {
    override fun toString() = "KndroidX.Tile"
}