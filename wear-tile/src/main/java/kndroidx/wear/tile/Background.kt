package kndroidx.wear.tile

import androidx.wear.protolayout.ColorBuilders
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.ModifiersBuilders

fun ShapeBackground(color: ColorBuilders.ColorProp, radius: DimensionBuilders.DpProp) =
    ModifiersBuilders.Background
        .Builder()
        .setColor(color)
        .setCorner(
            ModifiersBuilders.Corner.Builder()
                .setRadius(radius)
                .build()
        )
        .build()