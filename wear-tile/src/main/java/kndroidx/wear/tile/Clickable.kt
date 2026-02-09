package kndroidx.wear.tile

import androidx.wear.protolayout.ActionBuilders
import androidx.wear.protolayout.ModifiersBuilders

typealias ActivityBuilder = ActionBuilders.AndroidActivity.Builder
typealias LaunchActionBuilder = ActionBuilders.LaunchAction.Builder
typealias IntExtraBuilder = ActionBuilders.AndroidIntExtra.Builder
typealias LongExtraBuilder = ActionBuilders.AndroidLongExtra.Builder
typealias StringExtraBuilder = ActionBuilders.AndroidStringExtra.Builder
typealias DoubleExtraBuilder = ActionBuilders.AndroidDoubleExtra.Builder
typealias BooleanExtraBuilder = ActionBuilders.AndroidBooleanExtra.Builder

fun Clickable(id: String) =
    ModifiersBuilders.Clickable.Builder()
        .setId(id)
        .setOnClick(
            ActionBuilders.LoadAction.Builder()
                .build()
        )
        .build()

fun Clickable(
    id: String,
    packageName: String,
    className: String,
    block: (ActivityBuilder.() -> Unit)? = null
) =
    ModifiersBuilders.Clickable.Builder().apply {
        setOnClick(LaunchActionBuilder().apply {
            setId(id)
            setAndroidActivity(ActivityBuilder().apply {
                block?.invoke(this)
                setPackageName(packageName)
                setClassName(className)
            }.build())
        }.build())
    }.build()


fun ActivityBuilder.int(id: String, value: Int) =
    addKeyToExtraMapping(id, IntExtraBuilder().setValue(value).build())

fun ActivityBuilder.long(id: String, value: Long) =
    addKeyToExtraMapping(id, LongExtraBuilder().setValue(value).build())

fun ActivityBuilder.string(id: String, value: String) =
    addKeyToExtraMapping(id, StringExtraBuilder().setValue(value).build())

fun ActivityBuilder.double(id: String, value: Double) =
    addKeyToExtraMapping(id, DoubleExtraBuilder().setValue(value).build())

fun ActivityBuilder.boolean(id: String, value: Boolean) =
    addKeyToExtraMapping(id, BooleanExtraBuilder().setValue(value).build())
