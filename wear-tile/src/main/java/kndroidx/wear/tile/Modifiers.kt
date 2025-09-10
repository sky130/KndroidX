@file:OptIn(ProtoLayoutExperimental::class)

package kndroidx.wear.tile

import androidx.annotation.OptIn
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.DimensionBuilders.ContainerDimension
import androidx.wear.protolayout.DimensionBuilders.DpProp
import androidx.wear.protolayout.DimensionBuilders.expand
import androidx.wear.protolayout.DimensionBuilders.wrap
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.ModifiersBuilders.AnimatedVisibility
import androidx.wear.protolayout.ModifiersBuilders.Background
import androidx.wear.protolayout.ModifiersBuilders.Border
import androidx.wear.protolayout.ModifiersBuilders.Clickable
import androidx.wear.protolayout.ModifiersBuilders.Semantics
import androidx.wear.protolayout.TypeBuilders
import androidx.wear.protolayout.expression.ProtoLayoutExperimental
import androidx.wear.protolayout.ModifiersBuilders.ArcModifiers.Builder as ArcModifiersBuilder
import androidx.wear.protolayout.ModifiersBuilders.Modifiers.Builder as ModifiersBuilder

val Modifier get() = ModifierWrapper(ModifiersBuilder())

typealias Wrapper = ModifierWrapper

class ModifierWrapper(val builder: ModifiersBuilder) {
    internal lateinit var width: DpProp
    internal lateinit var height: DpProp
    internal lateinit var containerWidth: ContainerDimension
    internal lateinit var containerHeight: ContainerDimension
    internal lateinit var id: String

    internal fun layout(
        width: (ContainerDimension) -> Unit,
        height: (ContainerDimension) -> Unit
    ) {
        width(
            if (::containerWidth.isInitialized) {
                containerHeight
            } else {
                this.width
            }
        )
        height(
            if (::containerHeight.isInitialized) {
                containerHeight
            } else {
                this.height
            }
        )
    }

    fun arc() = ArcModifierWrapper(ArcModifiersBuilder())
    fun build() = builder.build()
}

fun Wrapper.wrapContentSize() = apply {
    containerWidth = wrap()
    containerHeight = wrap()
}

fun Wrapper.weight(weight: Float) = apply {
    weight(weight, weight)
}

fun Wrapper.weight(width: Float? = null, height: Float? = null) = apply {
    width?.let { containerWidth = DimensionBuilders.weight(it) }
    height?.let { containerHeight = DimensionBuilders.weight(it) }
}

fun Wrapper.fillMaxWidth() = apply { containerWidth = expand() }

fun Wrapper.fillMaxHeight() = apply { containerWidth = expand() }

fun Wrapper.fillMaxSize() = apply {
    fillMaxWidth()
    fillMaxHeight()
}

fun Wrapper.width(value: DpProp) = apply { width = value }

fun Wrapper.height(value: DpProp) = apply { height = value }

fun Wrapper.size(value: DpProp) {
    width(value)
    height(value)
}

fun Wrapper.id(id: String) = apply { this.id = id }

fun Wrapper.visible(visible: Boolean) =
    apply { builder.setVisible(TypeBuilders.BoolProp.Builder(visible).build()) }

fun Wrapper.background(background: Background) = apply { builder.setBackground(background) }

fun Wrapper.clickable(clickable: Clickable) = apply { builder.setClickable(clickable) }

fun Wrapper.border(border: Border) = apply { builder.setBorder(border) }

fun Wrapper.animation(animatedVisibility: AnimatedVisibility) =
    apply { builder.setContentUpdateAnimation(animatedVisibility) }

fun Wrapper.semantics(block: Semantics.Builder.() -> Unit) =
    apply { builder.setSemantics(Semantics.Builder().apply(block).build()) }

fun Wrapper.padding(
    top: DpProp? = null,
    bottom: DpProp? = null,
    start: DpProp? = null,
    end: DpProp? = null,
) = apply { builder.setPadding(Padding(top, bottom, start, end, null, null)) }

fun Wrapper.padding(
    vertical: DpProp? = null,
    horizontal: DpProp? = null,
) = apply { builder.setPadding(Padding(null, null, null, null, vertical, horizontal)) }

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