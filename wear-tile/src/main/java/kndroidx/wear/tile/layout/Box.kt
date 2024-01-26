package kndroidx.wear.tile.layout

import androidx.wear.protolayout.DimensionBuilders.ContainerDimension
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ModifiersBuilders.Modifiers.Builder as ModifiersBuilder

fun Box(
    width: ContainerDimension,
    height: ContainerDimension,
    modifier: ModifiersBuilder? = null,
    block: (LayoutElementBuilders.Box.Builder.() -> Unit)? = null,
) = LayoutElementBuilders.Box.Builder().apply {
    setWidth(width)
    setHeight(height)
    modifier?.let { setModifiers(it.build()) }
    block?.invoke(this)
}.build()