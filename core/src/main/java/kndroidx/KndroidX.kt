@file:SuppressLint("StaticFieldLeak")

package kndroidx

import android.annotation.SuppressLint
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

inline fun <T> kndroidx(block: KndroidX.() -> T): T {
    return KndroidX.block()
}

fun kndroidxConfig(block: KndroidConfig.() -> Unit) {
    KndroidConfig.block()
}

@SuppressLint("StaticFieldLeak")
object KndroidConfig {
    var context: Context? = null
        get() = field.apply {
            if (this == null) {
                throw IllegalStateException("Didn't Init KndroidX")
            }
        }
}

object KndroidX {
    val job = Job()
    val scope = CoroutineScope(job)
    val context get() = KndroidConfig.context!!
}