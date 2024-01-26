package kndroidx.wear.tile.layout

import androidx.wear.protolayout.DimensionBuilders.ContainerDimension
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ModifiersBuilders.Modifiers.Builder as ModifiersBuilder

fun Row(
    width: ContainerDimension,
    height: ContainerDimension,
    modifier: ModifiersBuilder? = null,
    block: (LayoutElementBuilders.Row.Builder.() -> Unit)? = null,
) = LayoutElementBuilders.Row.Builder().apply {
    setWidth(width)
    setHeight(height)
    modifier?.let { setModifiers(it.build()) }
    block?.invoke(this)
}.build()