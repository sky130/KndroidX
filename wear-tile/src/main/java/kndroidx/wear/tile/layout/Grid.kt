package kndroidx.wear.tile.layout

import androidx.wear.protolayout.DimensionBuilders.ContainerDimension
import androidx.wear.protolayout.DimensionBuilders.expand
import androidx.wear.protolayout.DimensionBuilders.weight
import androidx.wear.protolayout.DimensionBuilders.wrap
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.LayoutElementBuilders.Row.*
import kndroidx.wear.tile.dp
import androidx.wear.protolayout.ModifiersBuilders.Modifiers.Builder as ModifiersBuilder

@Suppress("FunctionName")
fun Any.Grid(
    width: ContainerDimension,
    height: ContainerDimension,
    spanCount: Int = 1,
    modifiers: ModifiersBuilder? = null,
    rowModifiers: ModifiersBuilder? = null,
    block: Grid.() -> Unit = {},
) = Column(width, height, modifiers) {
    if (spanCount < 1) throw IllegalStateException("SpanCount cannot be $spanCount.")
    Grid(this, spanCount, rowModifiers).apply(block).build()
}

class Grid(
    private val column: LayoutElementBuilders.Column.Builder,
    private val spanCount: Int,
    private val rowModifiers: ModifiersBuilder? = null,
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
                row.addContent(Box(width = weight(1f), height = 0.dp))
                x--
            }
        }.build())
    }

    private fun getRowBuilder() =
        Builder().setHeight(wrap()).setWidth(expand()).apply {
            rowModifiers?.let { setModifiers(it.build()) }
//            setModifiers(ModifiersBuilderBuilder().apply {
//                setVerticalAlignment(Vertical(LayoutElementBuilders.VERTICAL_ALIGN_CENTER))
//            }.setPadding(ModifiersBuilders.Padding.Builder().apply {
//                rowPadding?.let {
//                    setEnd(it.end)
//                    setTop(it.top)
//                    setBottom(it.bottom)
//                    setStart(it.start)
//                }
//            }.build()).build())
        }

    fun contents(vararg elements: LayoutElementBuilders.LayoutElement) {
        list.addAll(elements)
    }
}