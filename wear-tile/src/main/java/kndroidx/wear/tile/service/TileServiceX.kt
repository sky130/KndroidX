package kndroidx.wear.tile.service

import androidx.annotation.DrawableRes
import androidx.collection.ArrayMap
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.TimelineBuilders
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

abstract class TileServiceX : TileService() {
    private val imageMap = ArrayMap<String, Image>()
    private val clickableMap = ArrayMap<String, () -> Unit>()
    abstract val version: String

    abstract fun onClick(id: String)
    abstract fun onResourcesRequest()
    abstract fun onLayout(): LayoutElementBuilders.LayoutElement

    protected fun autoHandle(id: String) {
        clickableMap[id]?.invoke()
    }

    fun Clickable(id: String, block: () -> Unit) = Clickable(id).apply {
        clickableMap[id] = block
    }

    @Suppress("FunctionName")
    fun ResImage(id: String, @DrawableRes resId: Int) {
        imageMap[id] = ResImage(resId)
    }

    @Suppress("FunctionName")
    fun ByteImage(id: String, byteArray: ByteArray) {
        imageMap[id] = ByteImage(byteArray)
    }

    override fun onTileRequest(requestParams: RequestBuilders.TileRequest): ListenableFuture<TileBuilders.Tile> =
        Futures.immediateFuture(
            TileBuilders.Tile.Builder()
                .apply {
                    if (requestParams.currentState.lastClickableId.isNotEmpty())
                        onClick(requestParams.currentState.lastClickableId)
                }
                .setResourcesVersion(version)
                .setTileTimeline(
                    TimelineBuilders.Timeline.fromLayoutElement(
                        onLayout()
                    )
                ).build()
        )

    override fun onTileResourcesRequest(requestParams: RequestBuilders.ResourcesRequest): ListenableFuture<ResourceBuilders.Resources> =
        Futures.immediateFuture(
            ResourceBuilders.Resources.Builder().setVersion(version)
                .apply {
                    for ((id, res) in imageMap) {
                        when (res) {
                            is ResImage -> {
                                addImage(id, res.resId)
                            }

                            is ByteImage -> {
                                addImage(id, res.byte)
                            }
                        }
                    }

                }.build()
        )

    fun ResourceBuilders.Resources.Builder.addImage(itStr: String, id: Int) =
        this.addIdToImageMapping(
            itStr, ResourceBuilders.ImageResource.Builder().setAndroidResourceByResId(
                ResourceBuilders.AndroidImageResourceByResId.Builder().setResourceId(id).build()
            ).build()
        )

    fun ResourceBuilders.Resources.Builder.addImage(
        itStr: String,
        byte: ByteArray,
        widthPx: Int = 48,
        heightPx: Int = 48
    ) =
        this.addIdToImageMapping(
            itStr, ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(byte)
                        .setWidthPx(widthPx)
                        .setHeightPx(heightPx)
                        .setFormat(ResourceBuilders.IMAGE_FORMAT_RGB_565)
                        .build()
                ).build()
        ).build()
    fun ArcWrapper.onClick(block: () -> Unit) = apply { clickable(Clickable(id, block)) }

    fun Wrapper.onClick(block: () -> Unit) = apply { clickable(Clickable(id, block)) }
}