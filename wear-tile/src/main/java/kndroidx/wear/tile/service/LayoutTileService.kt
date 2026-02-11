package kndroidx.wear.tile.service

import androidx.annotation.DrawableRes
import androidx.collection.ArrayMap
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.ResourceBuilders.ImageFormat
import androidx.wear.protolayout.TimelineBuilders.Timeline.fromLayoutElement
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.TileService
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import kndroidx.wear.tile.ArcWrapper
import kndroidx.wear.tile.Clickable
import kndroidx.wear.tile.Wrapper
import kndroidx.wear.tile.clickable
import kndroidx.wear.tile.entity.ByteImage
import kndroidx.wear.tile.entity.Image
import kndroidx.wear.tile.entity.ResImage

abstract class LayoutTileService : TileService() {
    private val imageMap = ArrayMap<String, Image>()
    private val clickableMap = ArrayMap<String, () -> Unit>()

    abstract val version: String
    abstract fun onClick(id: String)
    abstract fun onResourcesRequest()
    abstract fun onLayout(): LayoutElementBuilders.LayoutElement

    init {
        onResourcesRequest()
    }

    protected fun invokeById(id: String) {
        clickableMap[id]?.invoke()
    }

    override fun onTileRequest(requestParams: RequestBuilders.TileRequest): ListenableFuture<TileBuilders.Tile> =
        TileBuilders.Tile.Builder()
            .also { handleOnClickRequest(requestParams) }
            .setResourcesVersion(version)
            .setTileTimeline(fromLayoutElement(onLayout()))
            .build()
            .let(Futures::immediateFuture)

    override fun onTileResourcesRequest(requestParams: RequestBuilders.ResourcesRequest): ListenableFuture<ResourceBuilders.Resources> =
        ResourceBuilders.Resources.Builder()
            .setVersion(version)
            .addImages()
            .build()
            .let(Futures::immediateFuture)

    private fun ResourceBuilders.Resources.Builder.addImages() = apply {
        for ((id, res) in imageMap) {
            when (res) {
                is ResImage -> addImage(id, res.resId)
                is ByteImage -> addImage(id, res.byte)
            }
        }
    }

    private fun handleOnClickRequest(requestParams: RequestBuilders.TileRequest) {
        requestParams.currentState.lastClickableId
            .takeIf(String::isNotEmpty)
            ?.also(::onClick)
    }

    fun ResourceBuilders.Resources.Builder.addImage(itStr: String, id: Int) =
        ResourceBuilders.AndroidImageResourceByResId.Builder()
            .setResourceId(id)
            .build()
            .let {
                ResourceBuilders.ImageResource.Builder()
                    .setAndroidResourceByResId(it)
                    .build()
            }
            .let { addIdToImageMapping(itStr, it) }
            .build()

    fun ResourceBuilders.Resources.Builder.addImage(
        itStr: String,
        byte: ByteArray,
        widthPx: Int = 48,
        heightPx: Int = 48,
        @ImageFormat format: Int = ResourceBuilders.IMAGE_FORMAT_RGB_565,
    ) =
        ResourceBuilders.InlineImageResource.Builder()
            .setData(byte)
            .setWidthPx(widthPx)
            .setHeightPx(heightPx)
            .setFormat(format)
            .build()
            .let {
                ResourceBuilders.ImageResource.Builder()
                    .setInlineResource(it)
                    .build()
            }
            .let { addIdToImageMapping(itStr, it) }
            .build()

    fun ArcWrapper.onClick(block: () -> Unit) = apply { clickable(Clickable(id, block)) }

    fun Wrapper.onClick(block: () -> Unit) = apply { clickable(Clickable(id, block)) }

    fun Clickable(id: String, block: () -> Unit) = Clickable(id).apply { clickableMap[id] = block }

    @Suppress("FunctionName")
    fun LayoutTileService.ResImage(id: String, @DrawableRes resId: Int) {
        imageMap[id] = ResImage(resId)
    }

    @Suppress("FunctionName")
    fun LayoutTileService.ByteImage(id: String, byteArray: ByteArray) {
        imageMap[id] = ByteImage(byteArray)
    }
}