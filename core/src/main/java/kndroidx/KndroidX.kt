package kndroidx

import android.annotation.SuppressLint
import android.content.Context

fun kndroidx(block: KndroidX.() -> Unit) {
    KndroidX.block()
}

@SuppressLint("StaticFieldLeak")
object KndroidX {
    private var _context: Context? = null
    var context: Context
        set(value) {
            _context = value
        }
        get() {
            return _context ?: throw NullPointerException("KndroidX didn't init.")
        }
}