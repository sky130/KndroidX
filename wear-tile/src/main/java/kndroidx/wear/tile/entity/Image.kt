package kndroidx.wear.tile.entity

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.ResourceBuilders.ImageFormat

sealed class Image()

data class ResImage(@param:DrawableRes val resId: Int) : Image()

data class ByteImage(
    val byte: ByteArray,
    val widthPx: Int = 48,
    val heightPx: Int = 48,
    @field:ImageFormat val format: Int = ResourceBuilders.IMAGE_FORMAT_RGB_565
) : Image() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ByteImage

        if (widthPx != other.widthPx) return false
        if (heightPx != other.heightPx) return false
        if (format != other.format) return false
        if (!byte.contentEquals(other.byte)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = widthPx
        result = 31 * result + heightPx
        result = 31 * result + format
        result = 31 * result + byte.contentHashCode()
        return result
    }
}

@SuppressLint("SupportAnnotationUsage")
@DrawableRes
fun Int.toImage() = ResImage(this)

fun ByteArray.toImage() = ByteImage(this)