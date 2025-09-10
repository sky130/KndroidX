package kndroidx.wear.tile.layout

import androidx.wear.protolayout.DimensionBuilders.expand
import androidx.wear.protolayout.DimensionBuilders.wrap
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.LayoutElementBuilders.Row.Builder
import kndroidx.wear.tile.Modifier
import kndroidx.wear.tile.ModifierWrapper
import kndroidx.wear.tile.Tile
import kndroidx.wear.tile.dp
import kndroidx.wear.tile.height
import kndroidx.wear.tile.weight

@Suppress("FunctionName")
fun Any.Grid(
    spanCount: Int = 1,
    modifier: ModifierWrapper? = null,
    rowModifier: ModifierWrapper? = null,
    block: Grid.() -> Unit = {},
) = Column(modifier) {
    if (spanCount < 1) throw IllegalStateException("SpanCount cannot be $spanCount.")
    Grid(this, spanCount, rowModifier).apply(block).build()
}

class Grid(
    private val column: LayoutElementBuilders.Column.Builder,
    private val spanCount: Int,
    private val rowModifiers: ModifierWrapper? = null,
) {
    private val list = arrayListOf<LayoutElementBuilders.LayoutElement>()

    fun build() {
        var row = getRowBuilder()
        for ((index, view) in list.withIndex()) {
            if (index % spanCount == 0) {
                column.addContent(row.build())
                row = getRowBuilder()
            }
            row.addContent(view)
        }
        column.addContent(row.apply {
            var x = spanCount - row.build().contents.size
            while (x != 0) {
                row.addContent(
                    Tile.Box(
                        Modifier
                            .weight(width = 1f)
                            .height(0.dp)
                    )
                )
                x--
            }
        }.build())
    }

    private fun getRowBuilder() =
        Builder().setHeight(wrap()).setWidth(expand()).apply {
            rowModifiers?.let { setModifiers(it.build()) }
        }

    fun contents(vararg elements: LayoutElementBuilders.LayoutElement) {
        list.addAll(elements)
    }
}