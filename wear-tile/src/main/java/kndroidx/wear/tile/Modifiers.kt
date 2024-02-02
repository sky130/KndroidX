package kndroidx.wear.tile

import androidx.annotation.OptIn
import androidx.wear.protolayout.DimensionBuilders.DpProp
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.ModifiersBuilders.AnimatedVisibility
import androidx.wear.protolayout.ModifiersBuilders.Background
import androidx.wear.protolayout.ModifiersBuilders.Border
import androidx.wear.protolayout.ModifiersBuilders.Clickable
import androidx.wear.protolayout.ModifiersBuilders.Semantics
import androidx.wear.protolayout.TypeBuilders
import androidx.wear.protolayout.expression.ProtoLayoutExperimental
import androidx.wear.protolayout.ModifiersBuilders.Modifiers.Builder as ModifiersBuilder


val Modifier get() = ModifiersBuilder()

@OptIn(ProtoLayoutExperimental::class)
fun ModifiersBuilder.visible(visible: Boolean) =
    setVisible(TypeBuilders.BoolProp.Builder().setValue(visible).build())

fun ModifiersBuilder.background(background: Background) = setBackground(background)

fun ModifiersBuilder.clickable(clickable: Clickable) = setClickable(clickable)

fun ModifiersBuilder.border(border: Border) = setBorder(border)

@OptIn(ProtoLayoutExperimental::class)
fun ModifiersBuilder.animation(animatedVisibility: AnimatedVisibility) = setContentUpdateAnimation(animatedVisibility)

fun ModifiersBuilder.semantics(semantics: Semantics) = setSemantics(semantics)

fun ModifiersBuilder.padding(
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