package kndroidx.wear.tile

import androidx.wear.protolayout.DimensionBuilders.expand
import androidx.wear.tiles.TileService
import kndroidx.wear.tile.layout.Box
import androidx.wear.protolayout.LayoutElementBuilders.Box.Builder as BoxBuilder

fun TileService.layout(block: BoxBuilder.() -> Unit) = Box(expand(), expand(), block = block)
