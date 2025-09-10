package kndroidx.wear.tile

import androidx.wear.protolayout.ColorBuilders
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ModifiersBuilders.Clickable
import androidx.wear.protolayout.ModifiersBuilders.ArcModifiers.Builder as ArcModifiersBuilder

typealias ArcWrapper = ArcModifierWrapper

class ArcModifierWrapper(val builder: ArcModifiersBuilder) {
    internal lateinit var id: String
    internal var thickness: DimensionBuilders.DpProp? = null
    internal var color: ColorBuilders.ColorProp? = null
    internal var brush: ColorBuilders.Brush? = null
    internal var arcDirection: LayoutElementBuilders.ArcDirectionProp? = null
    internal var strokeCap: LayoutElementBuilders.StrokeCapProp? = null
    fun build() = builder.build()
}

fun ArcModifierWrapper.id(id: String) = apply { this.id = id }

fun ArcWrapper.clickable(clickable: Clickable) = apply { builder.setClickable(clickable) }

fun ArcModifierWrapper.thickness(value: DimensionBuilders.DpProp) = apply { thickness = value }

fun ArcModifierWrapper.color(value: ColorBuilders.ColorProp) = apply { color = value }

fun ArcModifierWrapper.brush(value: ColorBuilders.Brush) = apply { brush = value }

fun ArcModifierWrapper.arcDirection(value: LayoutElementBuilders.ArcDirectionProp) =
    apply { arcDirection = value }

fun ArcModifierWrapper.strokeCap(value: LayoutElementBuilders.StrokeCapProp) =
    apply { strokeCap = value }
