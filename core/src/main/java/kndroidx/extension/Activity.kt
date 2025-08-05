package kndroidx.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import java.io.Serializable

inline fun <reified T : Serializable> ComponentActivity.extra(key: String) = lazy {
    @Suppress("DEPRECATION")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) intent.extras!!.getSerializable(
        key, T::class.java
    )
    else intent.extras!!.getSerializable(key) as T
}


inline fun <reified T : Activity> Context.start(options: Bundle, block: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.block()
    startActivity(intent, options)
}


