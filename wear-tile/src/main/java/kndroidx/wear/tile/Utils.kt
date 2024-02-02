package kndroidx.wear.tile

import androidx.wear.protolayout.ColorBuilders.argb
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.LayoutElementBuilders.Box
import androidx.wear.protolayout.LayoutElementBuilders.Column
import androidx.wear.protolayout.LayoutElementBuilders.LayoutElement
import androidx.wear.protolayout.LayoutElementBuilders.Row
import androidx.wear.tiles.TileService
import kndroidx.wear.tile.layout.Grid

val Number.dp get() = DimensionBuilders.dp(this.toFloat())

val Number.color get() = argb(toInt())

@Suppress("FunctionName")
fun Horizontal(value: Int) = LayoutElementBuilders.HorizontalAlignmentProp.Builder().setValue(
    value
).build()

@Suppress("FunctionName")
fun Vertical(value: Int) = LayoutElementBuilders.VerticalAlignmentProp.Builder().setValue(
    value
).build()


fun addLayoutElement(parent: Any, child: LayoutElement) {
    when (parent) {
        is Column.Builder -> {
            parent.addContent(child)
        }

        is Row.Builder -> {
            parent.addContent(child)
        }

        is Box.Builder -> {
            parent.addContent(child)
        }

        is Grid -> {
            parent.contents(child)
        }

        is TileService -> Unit

        is Tile -> Unit

        else -> {
            throw IllegalStateException("Tile Fun is only can be call in Layout Block.")
        }
    }
}


