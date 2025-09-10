package kndroidx.wear.tile.entity

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes

sealed class Image()

class ResImage(@param:DrawableRes val resId: Int) : Image()

class ByteImage(val byte: ByteArray) : Image()

@SuppressLint("SupportAnnotationUsage")
@DrawableRes
fun Int.toImage() = ResImage(this)

fun ByteArray.toImage() = ByteImage(this)