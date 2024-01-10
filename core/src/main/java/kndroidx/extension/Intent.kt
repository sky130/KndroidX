package kndroidx.extension

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun Context.startIntent(block: Intent.() -> Unit): Intent {
    val intent = Intent()
    intent.block()
    startActivity(intent)
    return intent
}

inline fun <reified T : Activity> Context.start() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

inline fun <reified T : Activity> Context.start(block: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.block()
    startActivity(intent)
}
