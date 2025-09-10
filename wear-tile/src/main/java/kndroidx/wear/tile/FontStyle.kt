package kndroidx.wear.tile

import androidx.annotation.OptIn
import androidx.wear.protolayout.ColorBuilders.ColorProp
import androidx.wear.protolayout.DimensionBuilders.EmProp
import androidx.wear.protolayout.DimensionBuilders.SpProp
import androidx.wear.protolayout.LayoutElementBuilders.FontSetting
import androidx.wear.protolayout.LayoutElementBuilders.FontStyle
import androidx.wear.protolayout.expression.ProtoLayoutExperimental

@OptIn(ProtoLayoutExperimental::class)
fun FontStyle(
    textSize: SpProp? = null,
    textColor: ColorProp? = null,
    textWeight: Int? = null,
    fontFamily: String? = null,
    fontSetting: Array<FontSetting>? = null,
    isItalic: Boolean? = null,
    isUnderline: Boolean? = null,
    letterSpacing: EmProp? = null,
    variant: Int? = null,
    fallbacks: Array<String>? = null
) = FontStyle.Builder().apply {
    isItalic?.let { setItalic(it) }
    textSize?.let { setSize(it) }
    textColor?.let { setColor(it) }
    letterSpacing?.let { setLetterSpacing(it) }
    textWeight?.let { setWeight(it) }
    isUnderline?.let { setUnderline(it) }
    setSettings(*(fontSetting ?: emptyArray()))
    fontFamily?.let { setPreferredFontFamilies(it, *(fallbacks ?: emptyArray())) }
    variant?.let { setVariant(it) }
}.build()


