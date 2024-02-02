package kndroidx.wear.tile

import androidx.wear.protolayout.ActionBuilders
import androidx.wear.protolayout.ModifiersBuilders

fun Clickable(id: String) = ModifiersBuilders.Clickable.Builder().apply {
    setId(id)
    setOnClick(ActionBuilders.LoadAction.Builder().build())
}.build()

fun Clickable(id: String, packageName: String, className: String) =
    ModifiersBuilders.Clickable.Builder().apply {
        setOnClick(ActionBuilders.LaunchAction.Builder().apply {
            setId(id)
            setAndroidActivity(ActionBuilders.AndroidActivity.Builder().apply {
                setPackageName(packageName)
                setClassName(className)
            }.build())
        }.build())
    }.build()
