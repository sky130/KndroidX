package kndroidx.wear.tile

import androidx.annotation.OptIn
import androidx.wear.protolayout.DimensionBuilders.DpProp
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.TypeBuilders
import androidx.wear.protolayout.expression.ProtoLayoutExperimental
import androidx.wear.protolayout.ModifiersBuilders.Modifiers.Builder as ModifiersBuilder


val Modifier get() = ModifiersBuilder()

@OptIn(ProtoLayoutExperimental::class)
fun ModifiersBuilder.setVisible(visible: Boolean) =
    setVisible(TypeBuilders.BoolProp.Builder().setValue(visible).build())

fun ModifiersBuilder.setPadding(
    top: DpProp?,
    bottom: DpProp?,
    start: DpProp?,
    end: DpProp?,
    vertical: DpProp?,
    horizontal: DpProp?,
) = setPadding(Padding(top, bottom, start, end, vertical, horizontal))

fun Padding(
    top: DpProp?,
    bottom: DpProp?,
    start: DpProp?,
    end: DpProp?,
    vertical: DpProp?,
    horizontal: DpProp?,
) =
    ModifiersBuilders.Padding.Builder().apply {
        top?.let { setTop(it) }
        bottom?.let { setBottom(it) }
        start?.let { setStart(it) }
        end?.let { setEnd(it) }
        vertical?.let { setTop(it) }
        vertical?.let { setBottom(it) }
        horizontal?.let { setStart(it) }
        horizontal?.let { setEnd(it) }
    }.build()